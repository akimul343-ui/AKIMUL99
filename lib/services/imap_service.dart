import 'package:audioplayers/audioplayers.dart';
import 'package:enough_mail/enough_mail.dart';
import 'package:flutter_overlay_window/flutter_overlay_window.dart';
import 'package:vibration/vibration.dart';
import '../models/app_settings.dart';

class ImapService {
  ImapClient? _client;
  String? _lastOtp;
  final AudioPlayer _audioPlayer = AudioPlayer();

  Future<void> startListening({
    required String email,
    required String appPassword,
    required AppSettings settings,
  }) async {
    _client = ImapClient(isLogEnabled: false);
    try {
      await _client!.connectToServer('imap.gmail.com', 993, isSecure: true);
      await _client!.login(email, appPassword);
      await _client!.selectInbox();

      _client!.eventBus.on<ImapNewMessageEvent>().listen((event) async {
        final fetchResult = await _client!.fetchMessage(event.serverSequence, 'BODY[]');
        final message = fetchResult.messages.first;

        if (message.fromEmail?.contains('telegram') ?? true) {
          String? body = message.decodeTextPlainPart();
          if (body != null) {
            RegExp exp = RegExp(r'\b\d{5,6}\b');
            String? otp = exp.firstMatch(body)?.group(0);

            if (otp != null && otp != _lastOtp) {
              _lastOtp = otp;
              _triggerAlerts(settings);
              await FlutterOverlayWindow.shareData({'otp': otp});
            }
          }
        }
      });
      await _client!.idleStart();
    } catch (_) {}
  }

  void _triggerAlerts(AppSettings settings) async {
    if (settings.enableSound) {
      await _audioPlayer.play(AssetSource('sounds/notification.mp3'));
    }
    if (settings.enableVibration && (await Vibration.hasVibrator() ?? false)) {
      Vibration.vibrate(duration: 500);
    }
  }

  void stop() => _client?.logout();
}
