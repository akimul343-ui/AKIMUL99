import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/app_settings.dart';

class EncryptedStorageService {
  final _secureStorage = const FlutterSecureStorage();

  Future<void> saveCredentials(String email, String appPassword) async {
    await _secureStorage.write(key: 'gmail_email', value: email);
    await _secureStorage.write(key: 'app_password', value: appPassword);
  }

  Future<Map<String, String?>> getCredentials() async {
    return {
      'email': await _secureStorage.read(key: 'gmail_email'),
      'password': await _secureStorage.read(key: 'app_password'),
    };
  }

  Future<void> saveSettings(AppSettings settings) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('app_settings', jsonEncode(settings.toJson()));
  }

  Future<AppSettings> getSettings() async {
    final prefs = await SharedPreferences.getInstance();
    String? rawData = prefs.getString('app_settings');
    return rawData != null ? AppSettings.fromJson(jsonDecode(rawData)) : AppSettings();
  }
}
