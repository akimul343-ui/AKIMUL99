import 'dart:math';

class RandomNameGenerator {
  static final List<String> _firstNames = ['Rahim', 'Karim', 'Tanvir', 'Sabbir', 'Arif', 'Nusrat', 'Sadia', 'Anika'];
  static final List<String> _lastNames = ['Ahmed', 'Khan', 'Chowdhury', 'Hossain', 'Islam', 'Rahman'];

  static String generate() {
    final random = Random();
    return "${_firstNames[random.nextInt(_firstNames.length)]} ${_lastNames[random.nextInt(_lastNames.length)]}";
  }
}
