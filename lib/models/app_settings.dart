class AppSettings {
  bool enableSound;
  bool enableVibration;
  bool enableCasePermutation;
  bool enableNameGenerator;
  bool glassmorphismEffect;

  AppSettings({
    this.enableSound = true,
    this.enableVibration = true,
    this.enableCasePermutation = true,
    this.enableNameGenerator = true,
    this.glassmorphismEffect = false,
  });

  Map<String, dynamic> toJson() => {
        'enableSound': enableSound,
        'enableVibration': enableVibration,
        'enableCasePermutation': enableCasePermutation,
        'enableNameGenerator': enableNameGenerator,
        'glassmorphismEffect': glassmorphismEffect,
      };

  factory AppSettings.fromJson(Map<String, dynamic> json) => AppSettings(
        enableSound: json['enableSound'] ?? true,
        enableVibration: json['enableVibration'] ?? true,
        enableCasePermutation: json['enableCasePermutation'] ?? true,
        enableNameGenerator: json['enableNameGenerator'] ?? true,
        glassmorphismEffect: json['glassmorphismEffect'] ?? false,
      );
}
