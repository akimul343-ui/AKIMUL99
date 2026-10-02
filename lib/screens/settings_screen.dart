import 'package:flutter/material.dart';
import '../models/app_settings.dart';
import '../services/storage_service.dart';

class SettingsScreen extends StatefulWidget {
  const SettingsScreen({Key? key}) : super(key: key);

  @override
  State<SettingsScreen> createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  final _storage = EncryptedStorageService();
  AppSettings _settings = AppSettings();

  @override
  void initState() {
    super.initState();
    _loadSettings();
  }

  void _loadSettings() async {
    final loaded = await _storage.getSettings();
    setState(() => _settings = loaded);
  }

  void _updateSetting(Function(AppSettings) update) {
    setState(() => update(_settings));
    _storage.saveSettings(_settings);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('বাংলা সেটিংস (Settings)'),
        backgroundColor: Colors.deepPurple,
      ),
      body: ListView(
        padding: const EdgeInsets.all(16.0),
        children: [
          _buildToggle('🔔 শব্দ নোটিফিকেশন (Sound Alert)', 'নতুন OTP আসলে সাউন্ড হবে', _settings.enableSound, (v) => _updateSetting((s) => s.enableSound = v)),
          _buildToggle('📳 ভাইব্রেশন (Vibration)', 'নতুন OTP আসলে ফোন কাঁপবে', _settings.enableVibration, (v) => _updateSetting((s) => s.enableVibration = v)),
          _buildToggle('🔠 অক্ষর ছোট-বড় বাটন (Case Permutations)', 'কেস চেঞ্জার কাস্টমাইজেশন', _settings.enableCasePermutation, (v) => _updateSetting((s) => s.enableCasePermutation = v)),
          _buildToggle('👤 নাম জেনারেটর (Random Name)', 'নাম কপি বাটন অন/অফ', _settings.enableNameGenerator, (v) => _updateSetting((s) => s.enableNameGenerator = v)),
          _buildToggle('👁️ ট্রান্সপারেন্ট বাবল (Glassmorphism)', 'ওভারলে ব্যাকগ্রাউন্ড গ্লাস ইফেক্ট', _settings.glassmorphismEffect, (v) => _updateSetting((s) => s.glassmorphismEffect = v)),
        ],
      ),
    );
  }

  Widget _buildToggle(String title, String subtitle, bool val, ValueChanged<bool> onChanged) {
    return Card(
      child: SwitchListTile(
        title: Text(title, style: const TextStyle(fontWeight: FontWeight.bold)),
        subtitle: Text(subtitle),
        value: val,
        onChanged: onChanged,
        activeColor: Colors.deepPurple,
      ),
    );
  }
}
