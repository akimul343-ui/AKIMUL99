package com.example.util

import android.content.Context
import android.content.SharedPreferences

enum class AppLanguage(val code: String, val displayName: String) {
    BANGLA("bn", "বাংলা"),
    ENGLISH("en", "English")
}

class LanguageManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("safeguard_lang_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_APP_LANGUAGE = "app_language"
    }

    fun getLanguage(): AppLanguage {
        val code = prefs.getString(KEY_APP_LANGUAGE, AppLanguage.BANGLA.code)
        return if (code == AppLanguage.ENGLISH.code) AppLanguage.ENGLISH else AppLanguage.BANGLA
    }

    fun setLanguage(language: AppLanguage) {
        prefs.edit().putString(KEY_APP_LANGUAGE, language.code).apply()
    }
}

/**
 * Bi-lingual text dictionary providing instant English & Bangla translations
 * for all features, tabs, cards, dialogs, and controls.
 */
object AppStrings {

    fun t(key: String, isBangla: Boolean): String {
        return if (isBangla) {
            banglaStrings[key] ?: key
        } else {
            englishStrings[key] ?: key
        }
    }

    private val banglaStrings = mapOf(
        // Tabs
        "tab_overview" to "ওভারভিউ",
        "tab_filters" to "ফিল্টার",
        "tab_quran" to "কুরআন ও গাইড",
        "tab_bedtime" to "নাইট কার্ফিউ",
        "tab_logs" to "লগ ও হিস্ট্রি",
        "tab_anti_uninstall" to "রিমুভ গার্ড",
        "tab_settings" to "সেটিংস",

        // Overview
        "app_title" to "Safe Guard",
        "app_subtitle" to "ডিজিটাল সুরক্ষা ও পবিত্র জীবনের গার্ডিয়ান",
        "shield_active" to "সুরক্ষা শিল্ড সক্রিয়",
        "shield_inactive" to "সুরক্ষা শিল্ড নিষ্ক্রিয়",
        "shield_active_desc" to "অশ্লীল সাইট, ক্ষতিকর কনটেন্ট ও ট্র্যাকার ব্লক করা হচ্ছে।",
        "shield_inactive_desc" to "আপনার মোবাইল এখনো সুরক্ষিত নয়। অবিলম্বে শিল্ড চালু করুন।",
        "btn_turn_on" to "শিল্ড চালু করুন",
        "btn_turn_off" to "শিল্ড বন্ধ করুন",
        "stat_blocked_requests" to "ব্লক হওয়া রিকোয়েস্ট",
        "stat_active_rules" to "সক্রিয় ফিল্টার রুলস",
        "stat_safe_rate" to "সুরক্ষা মাত্রা",
        "stat_anti_uninstall" to "অ্যান্টি-আনইন্সটল",
        "active" to "সক্রিয়",
        "inactive" to "নিষ্ক্রিয়",

        // Features Grid
        "feature_hub_title" to "সকল স্বাধীন ফিচার ও সুবিধা",
        "feature_hub_desc" to "প্রতিটি ফিচার সম্পূর্ণ আলাদা ও স্বাধীন। আপনার যা প্রয়োজন কেবল সেটাই ব্যবহার করুন।",
        "feat_dns_title" to "🛡️ ফুলস্ক্রিন ফোন লক গার্ড",
        "feat_dns_desc" to "কড়া শিডিউল ও জরুরি সুরক্ষায় পুরো ফোন লক রাখুন।",
        "feat_curfew_title" to "🌙 নাইট কার্ফিউ ও ফোন লক",
        "feat_curfew_desc" to "নির্দিষ্ট সময়ে (যেমন রাত ১১টা থেকে ভোর ৪টা) ফোন স্বয়ংক্রিয়ভাবে লক থাকবে।",
        "feat_quran_title" to "📖 অডিও কুরআন ও তিলাওয়াত",
        "feat_quran_desc" to "অর্থসহ তিলাওয়াত শুনুন এবং অফলাইনে শোনার জন্য ডাউনলোড করুন।",
        "feat_recovery_title" to "🤲 আসক্তি মুক্তির ইসলামিক গাইড",
        "feat_recovery_desc" to "দৃষ্টি সংযম, আমল ও প্রলোভন দমনের হাদিস ও কুরআনিক নির্দেশনা।",
        "feat_anti_uninstall_title" to "🔒 অ্যান্টি-আনইন্সটল ও পিন লক",
        "feat_anti_uninstall_desc" to "অ্যাপটি আনইন্সটল বা সেটিংস পরিবর্তন করতে গোপন পিন কোড প্রয়োজন।",
        "feat_logs_title" to "📊 রিয়েল-টাইম সিকিউরিটি হিস্ট্রি",
        "feat_logs_desc" to "কখন কোন খারাপ সাইট ব্লক করা হয়েছে তার পুঙ্খানুপুঙ্খ বিবরণ।",

        // Curfew
        "curfew_title" to "নাইট কার্ফিউ ও শিডিউল লক",
        "curfew_desc" to "নির্দিষ্ট সময়ে ফোন স্বয়ংক্রিয়ভাবে লক করে গভীর রাতে ফোন আসক্তি বন্ধ করুন।",
        "curfew_enable" to "কার্ফিউ লক সক্রিয় করুন",
        "curfew_enable_desc" to "নির্ধারিত সময়ে ফুলস্ক্রিন পর্দা লক চালু হবে",
        "curfew_start" to "লক শুরুর সময়",
        "curfew_end" to "আনলক হওয়ার সময়",
        "curfew_emergency" to "জরুরি আনলক (Emergency Override)",
        "curfew_emergency_desc" to "জরুরি প্রয়োজনে মাস্টার পিন দিয়ে ১৫ মিনিটের জন্য ফোন আনলক করুন।",

        // Quran
        "quran_header" to "পবিত্র কুরআন ও অন্তরের প্রশান্তি",
        "quran_sub" to "তিলাওয়াত শুনুন ও অফলাইনে শোনার জন্য সেভ রাখুন",
        "play" to "শুনুন",
        "pause" to "থামুন",
        "download" to "ডাউনলোড",
        "downloaded" to "অফলাইন",
        "verses" to "আয়াত",

        // Settings & Language
        "lang_switch_title" to "ভাষা পরিবর্তন (Language Switch)",
        "lang_current" to "বর্তমান ভাষা: বাংলা",
        "lang_change_to_en" to "English এ পরিবর্তন করুন",
        "lang_change_to_bn" to "বাংলায় পরিবর্তন করুন",
        "update_check" to "আপডেট চেক করুন",
        "version" to "ভার্সন"
    )

    private val englishStrings = mapOf(
        // Tabs
        "tab_overview" to "Overview",
        "tab_filters" to "Filters",
        "tab_quran" to "Quran & Guide",
        "tab_bedtime" to "Night Curfew",
        "tab_logs" to "Logs",
        "tab_anti_uninstall" to "Tamper Guard",
        "tab_settings" to "Settings",

        // Overview
        "app_title" to "Safe Guard",
        "app_subtitle" to "Digital Security & Islamic Lifestyle Guardian",
        "shield_active" to "Protection Shield Active",
        "shield_inactive" to "Protection Shield Inactive",
        "shield_active_desc" to "Adult websites, trackers, and malicious domains are strictly blocked.",
        "shield_inactive_desc" to "Your device is not fully protected. Turn on the shield now.",
        "btn_turn_on" to "Enable Shield",
        "btn_turn_off" to "Disable Shield",
        "stat_blocked_requests" to "Blocked Requests",
        "stat_active_rules" to "Active Rules",
        "stat_safe_rate" to "Security Level",
        "stat_anti_uninstall" to "Anti-Uninstall",
        "active" to "Active",
        "inactive" to "Inactive",

        // Features Grid
        "feature_hub_title" to "Independent Modular Features",
        "feature_hub_desc" to "Every feature is completely independent. Use whatever you need at your choice.",
        "feat_dns_title" to "🛡️ Full-Screen Phone Lock Guard",
        "feat_dns_desc" to "Strict schedule & emergency curfew phone lockdown.",
        "feat_curfew_title" to "🌙 Night Curfew & Screen Lockdown",
        "feat_curfew_desc" to "Restricts phone usage during scheduled hours (e.g. 11 PM to 4 AM).",
        "feat_quran_title" to "📖 Audio Quran & Translations",
        "feat_quran_desc" to "Listen to beautiful recitations with translations and download for offline.",
        "feat_recovery_title" to "🤲 Anti-Addiction Islamic Guidance",
        "feat_recovery_desc" to "Quranic wisdom, daily sunnah, and emergency prayers against temptations.",
        "feat_anti_uninstall_title" to "🔒 Anti-Uninstall & Master PIN",
        "feat_anti_uninstall_desc" to "Prevents bypassing or removing the app without entering your PIN.",
        "feat_logs_title" to "📊 Real-Time Inspection Logs",
        "feat_logs_desc" to "Live chronological security history of all filtered domains and attempts.",

        // Curfew
        "curfew_title" to "Night Curfew & Bedtime Lockdown",
        "curfew_desc" to "Automatically locks screen usage at night to eliminate late-night screen addiction.",
        "curfew_enable" to "Enable Curfew Lockdown",
        "curfew_enable_desc" to "Full-screen lock overlay activates during chosen hours",
        "curfew_start" to "Lock Start Time",
        "curfew_end" to "Auto-Unlock Time",
        "curfew_emergency" to "Emergency Override",
        "curfew_emergency_desc" to "Temporarily unlocks for 15 minutes by verifying your administrator PIN.",

        // Quran
        "quran_header" to "Holy Quran & Peace of Mind",
        "quran_sub" to "Listen to recitation and download for offline access",
        "play" to "Play",
        "pause" to "Pause",
        "download" to "Download",
        "downloaded" to "Offline",
        "verses" to "Verses",

        // Settings & Language
        "lang_switch_title" to "Language Settings",
        "lang_current" to "Current Language: English",
        "lang_change_to_en" to "Switch to English",
        "lang_change_to_bn" to "Switch to Bangla (বাংলা)",
        "update_check" to "Check for Updates",
        "version" to "Version"
    )
}
