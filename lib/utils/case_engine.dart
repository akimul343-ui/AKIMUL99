class CasePermutationEngine {
  int _currentIndex = 0;

  String getNextPermutation(String email) {
    if (!email.contains('@')) return email;
    final parts = email.split('@');
    final username = parts[0];
    final domain = parts[1];

    int totalPermutations = 1 << username.length;
    _currentIndex = (_currentIndex + 1) % totalPermutations;

    StringBuffer result = StringBuffer();
    for (int i = 0; i < username.length; i++) {
      if ((_currentIndex & (1 << i)) != 0) {
        result.write(username[i].toUpperCase());
      } else {
        result.write(username[i].toLowerCase());
      }
    }
    return "${result.toString()}@$domain";
  }
}
