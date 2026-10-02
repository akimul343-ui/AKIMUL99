import 'package:flutter/material.dart';
import 'package:flutter_overlay_window/flutter_overlay_window.dart';
import 'screens/settings_screen.dart';
import 'services/imap_service.dart';
import 'services/overlay_service.dart';
import 'services/storage_service.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MyApp());
}

@pragma("vm:entry-point")
void overlayMain() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MaterialApp(
    debugShowCheckedModeBanner: false,
    home: OverlayServiceUI(),
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'OTP Retriever',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Colors.deepPurple, useMaterial3: true),
      home: const HomeScreen(),
    );
  }
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final _emailCtrl = TextEditingController();
  final _passCtrl = TextEditingController();
  final _storage = EncryptedStorageService();
  final _imap = ImapService();
  bool _isRunning = false;

  void _toggleService() async {
    if (_isRunning) {
      _imap.stop();
      await FlutterOverlayWindow.closeOverlay();
      setState(() => _isRunning = false);
    } else {
      if (!await FlutterOverlayWindow.isPermissionGranted()) {
        await FlutterOverlayWindow.requestPermission();
        return;
      }
      await _storage.saveCredentials(_emailCtrl.text, _passCtrl.text);
      final settings = await _storage.getSettings();

      await FlutterOverlayWindow.showOverlay(enableDrag: true);
      _imap.startListening(email: _emailCtrl.text, appPassword: _passCtrl.text, settings: settings);
      setState(() => _isRunning = true);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Telegram OTP Retriever'),
        actions: [
          IconButton(
            icon: const Icon(Icons.settings),
            onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const SettingsScreen())),
          )
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(controller: _emailCtrl, decoration: const InputDecoration(labelText: 'Gmail Address', border: OutlineInputBorder())),
            const SizedBox(height: 12),
            TextField(controller: _passCtrl, obscureText: true, decoration: const InputDecoration(labelText: 'App Password', border: OutlineInputBorder())),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: _toggleService,
              style: ElevatedButton.styleFrom(backgroundColor: _isRunning ? Colors.red : Colors.green, minimumSize: const Size.fromHeight(50)),
              child: Text(_isRunning ? 'সার্ভিস বন্ধ করুন' : 'সার্ভিস চালু করুন', style: const TextStyle(color: Colors.white, fontSize: 18)),
            ),
          ],
        ),
      ),
    );
  }
}
