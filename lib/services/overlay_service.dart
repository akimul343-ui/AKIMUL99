import 'dartt:ui';
import 'package:flutter/material.dart';
import 'package:clipboard/clipboard.dart';
import 'package:flutter_overlay_window/flutter_overlay_window.dart';
import '../utils/case_engine.dart';
import '../utils/name_generator.dart';

class OverlayServiceUI extends StatefulWidget {
  const OverlayServiceUI({Key? key}) : super(key: key);

  @override
  State<OverlayServiceUI> createState() => _OverlayServiceUIState();
}

class _OverlayServiceUIState extends State<OverlayServiceUI> {
  String _otp = "---";
  String _email = "test@gmail.com";
  final _caseEngine = CasePermutationEngine();

  @override
  void initState() {
    super.initState();
    FlutterOverlayWindow.overlayListener.listen((data) {
      if (data is Map && data.containsKey('otp')) {
        setState(() => _otp = data['otp']);
        FlutterClipboard.copy(_otp);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      color: Colors.transparent,
      child: Center(
        child: ClipRRect(
          borderRadius: BorderRadius.circular(16),
          child: BackdropFilter(
            filter: ImageFilter.blur(sigmaX: 8, sigmaY: 8),
            child: Container(
              padding: const EdgeInsets.all(8),
              color: Colors.black.withOpacity(0.8),
              child: Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  IconButton(
                    icon: const Icon(Icons.text_fields, color: Colors.cyan),
                    onPressed: () => FlutterClipboard.copy(_caseEngine.getNextPermutation(_email)),
                  ),
                  IconButton(
                    icon: const Icon(Icons.swap_horiz, color: Colors.greenAccent),
                    onPressed: () => FlutterClipboard.copy(_email),
                  ),
                  ElevatedButton(
                    onPressed: () => FlutterClipboard.copy(_otp),
                    style: ElevatedButton.styleFrom(backgroundColor: Colors.deepPurple),
                    child: Text(_otp, style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold)),
                  ),
                  IconButton(
                    icon: const Icon(Icons.person, color: Colors.orangeAccent),
                    onPressed: () => FlutterClipboard.copy(RandomNameGenerator.generate()),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
