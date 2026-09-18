package com.example.islamic

import java.net.URLEncoder

data class QuranSurah(
    val number: Int,
    val nameArabic: String,
    val nameEnglish: String,
    val nameBangla: String,
    val versesCount: Int,
    val revelationType: String,
    val audioArabicUrl: String,
    val audioBanglaUrl: String,
    val banglaSummary: String
) {
    val audioUrl: String get() = audioArabicUrl
}

data class HadithItem(
    val id: String,
    val bookName: String,
    val chapterName: String,
    val hadithNumber: String,
    val arabicText: String,
    val banglaTranslation: String,
    val narrator: String,
    val significance: String
)

data class HadithBook(
    val id: String,
    val titleBangla: String,
    val titleArabic: String,
    val author: String,
    val description: String,
    val hadiths: List<HadithItem>
)

data class IslamicPoem(
    val id: String,
    val title: String,
    val poet: String,
    val theme: String,
    val stanzas: List<String>,
    val spiritualReflection: String
)

data class IslamicAdvice(
    val id: String,
    val category: String,
    val titleBangla: String,
    val arabicText: String?,
    val banglaTranslation: String,
    val reference: String,
    val practicalTip: String
)

object IslamicContentRepository {

    fun getRecommendedSurahs(): List<QuranSurah> = quranSurahs

    private fun buildBanglaArchiveUrl(fileName: String): String {
        val encoded = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20")
        return "https://archive.org/download/alquranwithbanglaaudio/$encoded"
    }

    val quranSurahs: List<QuranSurah> = listOf(
        QuranSurah(
            number = 1,
            nameArabic = "الفاتحة",
            nameEnglish = "Al-Fatihah",
            nameBangla = "আল-ফাতিহা",
            versesCount = 7,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/001.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("001 SURA FATIHA AND OPENING.mp3"),
            banglaSummary = "কুরআনের মূল নির্যাস ও শেফাদানকারী সর্বশ্রেষ্ঠ সূরা।"
        ),
        QuranSurah(
            number = 2,
            nameArabic = "البقرة",
            nameEnglish = "Al-Baqarah",
            nameBangla = "আল-বাকারা",
            versesCount = 286,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/002.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("002 SURA  BAQARA.mp3"),
            banglaSummary = "কুরআনের দীর্ঘতম সূরা; ঈমান, বিধিবিধান, আয়াতুল কুরসি ও তওবা।"
        ),
        QuranSurah(
            number = 3,
            nameArabic = "آل عمران",
            nameEnglish = "Ali Imran",
            nameBangla = "আলে ইমরান",
            versesCount = 200,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/003.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("003  SURA  AL  IMRAN.mp3"),
            banglaSummary = "তাওহীদ, বদর ও ওহুদ যুদ্ধের শিক্ষা এবং অবিচল থাকার প্রেরণা।"
        ),
        QuranSurah(
            number = 4,
            nameArabic = "النساء",
            nameEnglish = "An-Nisa",
            nameBangla = "আন-নিসা",
            versesCount = 176,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/004.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("004 SURA  AN  NISA.mp3"),
            banglaSummary = "নারী, এতিম ও পরিবারের অধিকার, পারিবারিক ন্যায়বিচার ও বিধান।"
        ),
        QuranSurah(
            number = 5,
            nameArabic = "المائدة",
            nameEnglish = "Al-Maidah",
            nameBangla = "আল-মায়িদাহ",
            versesCount = 120,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/005.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("005 SURA  AL  MAIDA.mp3"),
            banglaSummary = "হালাল-হারাম, অঙ্গীকার রক্ষা এবং দ্বীনের পরিপূর্ণতার ঘোষণা।"
        ),
        QuranSurah(
            number = 6,
            nameArabic = "الأنعام",
            nameEnglish = "Al-Anam",
            nameBangla = "আল-আনআম",
            versesCount = 165,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/006.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("006  SURA  AL  ANAM.mp3"),
            banglaSummary = "আল্লাহর একত্ববাদ, সৃষ্টিজগতের নিদর্শন ও শিরকের খণ্ডন।"
        ),
        QuranSurah(
            number = 7,
            nameArabic = "الأعراف",
            nameEnglish = "Al-Araf",
            nameBangla = "আল-আরাফ",
            versesCount = 206,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/007.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("007 SURA AL  ARAF.mp3"),
            banglaSummary = "নবীগণের দাওয়াত ও অহংকারী জাতির পরিণতির ইতিহাস।"
        ),
        QuranSurah(
            number = 8,
            nameArabic = "الأنفال",
            nameEnglish = "Al-Anfal",
            nameBangla = "আল-আনফাল",
            versesCount = 75,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/008.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("008 SURA  AN FAL.mp3"),
            banglaSummary = "বদর যুদ্ধ, গনিমতের বিধান এবং আল্লাহর ওপর পরম তাওয়াক্কুল।"
        ),
        QuranSurah(
            number = 9,
            nameArabic = "التوبة",
            nameEnglish = "At-Tawbah",
            nameBangla = "আত-তাওবাহ",
            versesCount = 129,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/009.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("009 SURA  TAWBA.mp3"),
            banglaSummary = "আন্তরিক তওবা, মুনাফিকদের চরিত্র ও দ্বীনের বিজয়।"
        ),
        QuranSurah(
            number = 10,
            nameArabic = "يونس",
            nameEnglish = "Yunus",
            nameBangla = "ইউনুস",
            versesCount = 109,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/010.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("010  SURA  YUNUS.mp3"),
            banglaSummary = "আল্লাহর হেদায়েত ও ইউনুস (আ.)-এর জাতির শিক্ষণীয় ঘটনা।"
        ),
        QuranSurah(
            number = 11,
            nameArabic = "هود",
            nameEnglish = "Hud",
            nameBangla = "হুদ",
            versesCount = 123,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/011.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("011  SURA  HUD.mp3"),
            banglaSummary = "হুদ, সালেহ ও লূত (আ.)-এর দাওয়াত এবং সত্যের পথে অটল থাকা।"
        ),
        QuranSurah(
            number = 12,
            nameArabic = "يوسف",
            nameEnglish = "Yusuf",
            nameBangla = "ইউসুফ",
            versesCount = 111,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/012.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("012  SURA  USUF.mp3"),
            banglaSummary = "ইউসুফ (আ.)-এর জীবনের শ্রেষ্ঠ কাহিনী—ধৈর্য ও আত্মসংযমের পাঠ।"
        ),
        QuranSurah(
            number = 13,
            nameArabic = "الرعد",
            nameEnglish = "Ar-Rad",
            nameBangla = "আর-রাদ",
            versesCount = 43,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/013.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("013  SURA  RAAD.mp3"),
            banglaSummary = "আল্লাহর মহাশক্তি ও কুরআনের সত্যতা; অন্তরের পরম প্রশান্তি।"
        ),
        QuranSurah(
            number = 14,
            nameArabic = "إبراهيم",
            nameEnglish = "Ibrahim",
            nameBangla = "ইব্রাহিম",
            versesCount = 52,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/014.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("014 SURA  IBRAHIM.mp3"),
            banglaSummary = "ইব্রাহিম (আ.)-এর পবিত্র আদর্শ ও কুফরির অসারতা।"
        ),
        QuranSurah(
            number = 15,
            nameArabic = "الحجر",
            nameEnglish = "Al-Hijr",
            nameBangla = "আল-হিজর",
            versesCount = 99,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/015.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("015  SURA  AL  HI JR.mp3"),
            banglaSummary = "কুরআন সংরক্ষণের চিরন্তন প্রতিশ্রুতি ও শয়তানের চ্যালেঞ্জ।"
        ),
        QuranSurah(
            number = 16,
            nameArabic = "النحل",
            nameEnglish = "An-Nahl",
            nameBangla = "আন-নাহল",
            versesCount = 128,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/016.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("016  SURA  AN  NAHAL.mp3"),
            banglaSummary = "নিয়ামতের সূরা; সৃষ্টির বৈচিত্র্য ও আল্লাহর অসীম অনুগ্রহ।"
        ),
        QuranSurah(
            number = 17,
            nameArabic = "الإسراء",
            nameEnglish = "Al-Isra",
            nameBangla = "আল-ইসরা / বনি ইসরাঈল",
            versesCount = 111,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/017.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("017 SURA  BANI  ISRAIEL.mp3"),
            banglaSummary = "মিরাজ গমন, মাতা-পিতার খেদমত ও আত্মশুদ্ধির মূলনীতি।"
        ),
        QuranSurah(
            number = 18,
            nameArabic = "الكهف",
            nameEnglish = "Al-Kahf",
            nameBangla = "আল-কাহাফ",
            versesCount = 110,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/018.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("018  SURA  AL  KAHAF.mp3"),
            banglaSummary = "জুমার দিনের নূর; দাজ্জালের ফেতনা থেকে বাঁচার রক্ষাকবচ।"
        ),
        QuranSurah(
            number = 19,
            nameArabic = "مريم",
            nameEnglish = "Maryam",
            nameBangla = "মারইয়াম",
            versesCount = 98,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/019.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("019  SURA  MARIYAM.mp3"),
            banglaSummary = "মারইয়াম (আ.) ও জাকারিয়া (আ.)-এর অলৌকিক ঘটনা ও তাওহীদ।"
        ),
        QuranSurah(
            number = 20,
            nameArabic = "طه",
            nameEnglish = "Taha",
            nameBangla = "ত্বোয়া-হা",
            versesCount = 135,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/020.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("020  SURA  TOAHA.mp3"),
            banglaSummary = "মূসা (আ.)-এর নবুওয়াত লাভ ও অন্তরে ঈমানী প্রশান্তি আনয়ন।"
        ),
        QuranSurah(
            number = 21,
            nameArabic = "الأنبياء",
            nameEnglish = "Al-Anbiya",
            nameBangla = "আল-আম্বিয়া",
            versesCount = 112,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/021.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("021 SURA  AMBIA.mp3"),
            banglaSummary = "বিভিন্ন নবীগণের দোআ কবুলের অনুপম দৃষ্টান্ত।"
        ),
        QuranSurah(
            number = 22,
            nameArabic = "الحج",
            nameEnglish = "Al-Hajj",
            nameBangla = "আল-হাজ্ব",
            versesCount = 78,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/022.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("022  SURA AL HAZZ.mp3"),
            banglaSummary = "কিয়ামতের ভয়াবহ দৃশ্য, হজের গুরুত্ব ও আত্মোৎসর্গ।"
        ),
        QuranSurah(
            number = 23,
            nameArabic = "المؤمنون",
            nameEnglish = "Al-Muminun",
            nameBangla = "আল-মুমিনুন",
            versesCount = 118,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/023.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("023  SURA  MOMINOON.mp3"),
            banglaSummary = "সফল মুমিনের গুণাবলী—লজ্জাস্থান ও দৃষ্টির পূর্ণ হেফাজত।"
        ),
        QuranSurah(
            number = 24,
            nameArabic = "النور",
            nameEnglish = "An-Nur",
            nameBangla = "আন-নূর",
            versesCount = 64,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/024.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("024  SURA AN NOOR.mp3"),
            banglaSummary = "পর্দা, চোখের কুদৃষ্টি বর্জন ও পারিবারিক পবিত্রতার বিধান।"
        ),
        QuranSurah(
            number = 25,
            nameArabic = "الفرقان",
            nameEnglish = "Al-Furqan",
            nameBangla = "আল-ফুরকান",
            versesCount = 77,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/025.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("025 SURA AL FOORCAN.mp3"),
            banglaSummary = "সত্য ও মিথ্যার পার্থক্যকারী এবং পরম দয়ালু আল্লাহর প্রিয় বান্দা।"
        ),
        QuranSurah(
            number = 26,
            nameArabic = "الشعراء",
            nameEnglish = "Ash-Shuara",
            nameBangla = "আশ-শুয়ারা",
            versesCount = 227,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/026.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("026 SURA  AS SOOARA.mp3"),
            banglaSummary = "নবীগণের সত্য প্রচারের ইতিহাস ও সৎকর্মের আহ্বান।"
        ),
        QuranSurah(
            number = 27,
            nameArabic = "النمل",
            nameEnglish = "An-Naml",
            nameBangla = "আন-নামল",
            versesCount = 93,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/027.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("027 SURA AN NAMAL.mp3"),
            banglaSummary = "সুলাইমান (আ.) ও পিঁপড়ার কাহিনী এবং আল্লাহর শুকরিয়া।"
        ),
        QuranSurah(
            number = 28,
            nameArabic = "القصص",
            nameEnglish = "Al-Qasas",
            nameBangla = "আল-কাসাস",
            versesCount = 88,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/028.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("028 SURA AL KASAS.mp3"),
            banglaSummary = "মূসা (আ.)-এর শৈশব থেকে বিজয় এবং কারুনের অহংকারের পতন।"
        ),
        QuranSurah(
            number = 29,
            nameArabic = "العنكبوت",
            nameEnglish = "Al-Ankabut",
            nameBangla = "আল-আনকাবুত",
            versesCount = 69,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/029.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("029 SURA AL  ANKABOOT.mp3"),
            banglaSummary = "ঈমানের পরীক্ষা, মাকড়সার ভঙ্গুর ঘর ও অবিচল সাধনা।"
        ),
        QuranSurah(
            number = 30,
            nameArabic = "الروم",
            nameEnglish = "Ar-Rum",
            nameBangla = "আর-রূম",
            versesCount = 60,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/030.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("030 SURA  RA ROOM.mp3"),
            banglaSummary = "ঐতিহাসিক ভবিষ্যৎবাণী ও স্বামী-স্ত্রীর পারস্পরিক ভালোবাসা।"
        ),
        QuranSurah(
            number = 31,
            nameArabic = "لقمان",
            nameEnglish = "Luqman",
            nameBangla = "লুকমান",
            versesCount = 34,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/031.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("031 SURA  LOOKMAN.mp3"),
            banglaSummary = "জ্ঞানী লুকমানের সন্তানকে দেওয়া চরিত্র গঠনের অমূল্য উপদেশ।"
        ),
        QuranSurah(
            number = 32,
            nameArabic = "السجدة",
            nameEnglish = "As-Sajdah",
            nameBangla = "আস-সাজদাহ",
            versesCount = 30,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/032.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("032 SURA AS SASDA.mp3"),
            banglaSummary = "ঘুমানোর আগের পাঠ্য; সৃষ্টির শুরু ও বিনম্র সেজদা।"
        ),
        QuranSurah(
            number = 33,
            nameArabic = "الأحزاب",
            nameEnglish = "Al-Ahzab",
            nameBangla = "আল-আহযাব",
            versesCount = 73,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/033.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("033 SURA AL AHZAB.mp3"),
            banglaSummary = "খন্দকের যুদ্ধ, নবীর উত্তম আদর্শ ও দরূদ শরীফের আদেশ।"
        ),
        QuranSurah(
            number = 34,
            nameArabic = "سبأ",
            nameEnglish = "Saba",
            nameBangla = "সাবা",
            versesCount = 54,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/034.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("034  SURA  SABA.mp3"),
            banglaSummary = "আল্লাহর অসীম ক্ষমতার কাছে মানুষের কৃতজ্ঞতার অপরিহার্যতা।"
        ),
        QuranSurah(
            number = 35,
            nameArabic = "فاطر",
            nameEnglish = "Fatir",
            nameBangla = "ফাতির",
            versesCount = 45,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/035.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("035  SURA  FATIR.mp3"),
            banglaSummary = "মহাবিশ্বের আদি স্রষ্টা এবং শয়তানের প্রতারণা থেকে সতর্কতা।"
        ),
        QuranSurah(
            number = 36,
            nameArabic = "يس",
            nameEnglish = "Ya-Sin",
            nameBangla = "ইয়াসীন",
            versesCount = 83,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/036.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("036  SURA  YASIN.mp3"),
            banglaSummary = "কুরআনের হৃদপিণ্ড; পুনরুত্থান ও পরকালের অকাট্য সত্যতা।"
        ),
        QuranSurah(
            number = 37,
            nameArabic = "الصافات",
            nameEnglish = "As-Saffat",
            nameBangla = "আস-সাফফাত",
            versesCount = 182,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/037.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("037  SURA  ASSAFFAT.mp3"),
            banglaSummary = "ফেরেশতাদের কাতার ও ইব্রাহিম (আ.)-এর আত্মত্যাগ।"
        ),
        QuranSurah(
            number = 38,
            nameArabic = "ص",
            nameEnglish = "Sad",
            nameBangla = "সোয়াদ",
            versesCount = 88,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/038.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("038 SURA  SWAD.mp3"),
            banglaSummary = "দাউদ ও আইয়ুব (আ.)-এর ধৈর্য এবং ইবলিসের অহংকার।"
        ),
        QuranSurah(
            number = 39,
            nameArabic = "الزمر",
            nameEnglish = "Az-Zumar",
            nameBangla = "আয-যুমার",
            versesCount = 75,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/039.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("039 SURA AZ  ZOOMAR.mp3"),
            banglaSummary = "আল্লাহর রহমত থেকে নিরাশ না হওয়ার শ্রেষ্ঠ সুসংবাদ।"
        ),
        QuranSurah(
            number = 40,
            nameArabic = "غافر",
            nameEnglish = "Ghafir",
            nameBangla = "গাফির / মুমিন",
            versesCount = 85,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/040.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("040 SURA AL  MOMIN.mp3"),
            banglaSummary = "গুনাহ মাফকারী পরম দয়ালু আল্লাহ ও দোআ কবুলের নিশ্চয়তা।"
        ),
        QuranSurah(
            number = 41,
            nameArabic = "فصلت",
            nameEnglish = "Fussilat",
            nameBangla = "ফুসসিলাত",
            versesCount = 54,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/041.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("041 SURA HAMIM AS SASDA.mp3"),
            banglaSummary = "কুরআনের স্পষ্ট বাণী ও সৎকাজে ধৈর্যশীলদের বিজয়।"
        ),
        QuranSurah(
            number = 42,
            nameArabic = "الشورى",
            nameEnglish = "Ash-Shura",
            nameBangla = "আশ-শূরা",
            versesCount = 53,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/042.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("042  SURA AS SURA.mp3"),
            banglaSummary = "পারস্পরিক পরামর্শের গুরুত্ব ও আল্লাহর ওপর ভরসা।"
        ),
        QuranSurah(
            number = 43,
            nameArabic = "الزخرف",
            nameEnglish = "Az-Zukhruf",
            nameBangla = "আয-যুখরুফ",
            versesCount = 89,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/043.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("043  SURA  AZZOKROOF.mp3"),
            banglaSummary = "পার্থিব চাকচিক্যের ক্ষণস্থায়িত্ব ও আখিরাতের স্থায়িত্ব।"
        ),
        QuranSurah(
            number = 44,
            nameArabic = "الدخان",
            nameEnglish = "Ad-Dukhan",
            nameBangla = "আদ-দুখান",
            versesCount = 59,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/044.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("044 SURA ADDOKHAN.mp3"),
            banglaSummary = "লাইলাতুল কদর ও কিয়ামতের পূর্বলক্ষণ ধোঁয়া।"
        ),
        QuranSurah(
            number = 45,
            nameArabic = "الجاثية",
            nameEnglish = "Al-Jathiyah",
            nameBangla = "আল-জাসিয়াহ",
            versesCount = 37,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/045.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("045  SURA  ZASYA.mp3"),
            banglaSummary = "মহাবিশ্বে চিন্তাশীলদের জন্য আল্লাহর নিদর্শনাবলী।"
        ),
        QuranSurah(
            number = 46,
            nameArabic = "الأحقاف",
            nameEnglish = "Al-Ahqaf",
            nameBangla = "আল-আহকাফ",
            versesCount = 35,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/046.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("046 SURA AL AHKAB.mp3"),
            banglaSummary = "মাতা-পিতার প্রতি সদ্ব্যবহারের নির্দেশ ও জিনদের ঈমান।"
        ),
        QuranSurah(
            number = 47,
            nameArabic = "محمد",
            nameEnglish = "Muhammad",
            nameBangla = "মুহাম্মদ",
            versesCount = 38,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/047.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("047 SURA  MOHAMMED.mp3"),
            banglaSummary = "রাসূল (সা.)-এর আনুগত্য ও ঈমানদারদের মর্যাদা।"
        ),
        QuranSurah(
            number = 48,
            nameArabic = "الفتح",
            nameEnglish = "Al-Fath",
            nameBangla = "আল-ফাতহ",
            versesCount = 29,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/048.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("048  SURA  FATTAH.mp3"),
            banglaSummary = "হুদায়বিয়ার সন্ধি ও ইসলামের সুস্পষ্ট বিজয়ের ঘোষণা।"
        ),
        QuranSurah(
            number = 49,
            nameArabic = "الحجرات",
            nameEnglish = "Al-Hujurat",
            nameBangla = "আল-হুজুরাত",
            versesCount = 18,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/049.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("049  SURA AL HUZURAT.mp3"),
            banglaSummary = "ইসলামিক শিষ্টাচার, গীবত বর্জন ও মানবতার ভ্রাতৃত্ববোধ।"
        ),
        QuranSurah(
            number = 50,
            nameArabic = "ق",
            nameEnglish = "Qaf",
            nameBangla = "ক্বাফ",
            versesCount = 45,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/050.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("050 SURA KAAF.mp3"),
            banglaSummary = "মৃত্যুযন্ত্রণা, পুনরুত্থান এবং মানুষের প্রতিটি কথার হিসাবরক্ষণ।"
        ),
        QuranSurah(
            number = 51,
            nameArabic = "الذاريات",
            nameEnglish = "Adh-Dhariyat",
            nameBangla = "আয-যারিয়াত",
            versesCount = 60,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/051.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("051  SURA  ZARIAT.mp3"),
            banglaSummary = "মানুষ ও জিন সৃষ্টির একমাত্র উদ্দেশ্য আল্লাহর ইবাদত।"
        ),
        QuranSurah(
            number = 52,
            nameArabic = "الطور",
            nameEnglish = "At-Tur",
            nameBangla = "আত-তূর",
            versesCount = 49,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/052.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("052 SURA  ATTOOR.mp3"),
            banglaSummary = "তূর পর্বতের শপথ ও পরকালের জবাবদিহিতা।"
        ),
        QuranSurah(
            number = 53,
            nameArabic = "النجم",
            nameEnglish = "An-Najm",
            nameBangla = "আন-নাজম",
            versesCount = 62,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/053.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("053 SURA ANNZAM.mp3"),
            banglaSummary = "মিরাজ রজনীর চাক্ষুষ সত্যতা ও কুপ্রবৃত্তির দাসত্ব বর্জন।"
        ),
        QuranSurah(
            number = 54,
            nameArabic = "القمر",
            nameEnglish = "Al-Qamar",
            nameBangla = "আল-ক্বামার",
            versesCount = 55,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/054.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("054 SURA KAMAR.mp3"),
            banglaSummary = "চাঁদ দ্বিখণ্ডিত হওয়া ও কুরআন শিক্ষার সহজতা।"
        ),
        QuranSurah(
            number = 55,
            nameArabic = "الرحمن",
            nameEnglish = "Ar-Rahman",
            nameBangla = "আর-রহমান",
            versesCount = 78,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/055.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("055 SURA AR RAHMAN.mp3"),
            banglaSummary = "কুরআনের রূপসী বধূ; আল্লাহর অগনিত নেয়ামতের স্বীকৃতি।"
        ),
        QuranSurah(
            number = 56,
            nameArabic = "الواقعة",
            nameEnglish = "Al-Waqiah",
            nameBangla = "আল-ওয়াকিয়াহ",
            versesCount = 96,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/056.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("056 SURA  WAKEYA.mp3"),
            banglaSummary = "দারিদ্র্য দূরকারী সূরা; মৃত্যুর পর মানুষের ৩টি শ্রেণি।"
        ),
        QuranSurah(
            number = 57,
            nameArabic = "الحديد",
            nameEnglish = "Al-Hadid",
            nameBangla = "আল-হাদীদ",
            versesCount = 29,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/057.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("057 SURA  HADID.mp3"),
            banglaSummary = "পার্থিব জীবনের মোহমুক্তি ও আল্লাহর পথে অর্থ ব্যয়ের গুরুত্ব।"
        ),
        QuranSurah(
            number = 58,
            nameArabic = "المجادلة",
            nameEnglish = "Al-Mujadilah",
            nameBangla = "আল-মুজাদালাহ",
            versesCount = 22,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/058.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("058  SURA  AL  MUZADALA.mp3"),
            banglaSummary = "আল্লাহর সার্বক্ষণিক নজরদারি ও পারিবারিক বিরোধ নিরসন।"
        ),
        QuranSurah(
            number = 59,
            nameArabic = "الحشر",
            nameEnglish = "Al-Hashr",
            nameBangla = "আল-হাশর",
            versesCount = 24,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/059.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("059 SURA AL HASHOR.mp3"),
            banglaSummary = "আসমাউল হুসনা (আল্লাহর সুন্দর নামসমূহ) ও বিনম্রতা।"
        ),
        QuranSurah(
            number = 60,
            nameArabic = "الممتحنة",
            nameEnglish = "Al-Mumtahanah",
            nameBangla = "আল-মুমতাহানাহ",
            versesCount = 13,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/060.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("060 SURA AL MOMTAHANA.mp3"),
            banglaSummary = "ঈমানের পরীক্ষা ও ইব্রাহিম (আ.)-এর জীবনাদর্শ।"
        ),
        QuranSurah(
            number = 61,
            nameArabic = "الصف",
            nameEnglish = "As-Saff",
            nameBangla = "আস-সাফ",
            versesCount = 14,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/061.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("061  SURA  ASSAF.mp3"),
            banglaSummary = "কথায় ও কাজে মিল রাখার কঠোর তাগিদ এবং ঐক্য।"
        ),
        QuranSurah(
            number = 62,
            nameArabic = "الجمعة",
            nameEnglish = "Al-Jumuah",
            nameBangla = "আল-জুমুআহ",
            versesCount = 11,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/062.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("062 SURA AL   ZOOMA.mp3"),
            banglaSummary = "জুমার সালাত ও বৈষয়িক ব্যস্ততা ছেড়ে মসজিদে গমন।"
        ),
        QuranSurah(
            number = 63,
            nameArabic = "المنافقون",
            nameEnglish = "Al-Munafiqun",
            nameBangla = "আল-মুনাফিকুন",
            versesCount = 11,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/063.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("063 SURA MONAFEKOON.mp3"),
            banglaSummary = "মুনাফিকদের লক্ষণ ও মৃত্যুর আগে সৎকাজের আকুল ইচ্ছা।"
        ),
        QuranSurah(
            number = 64,
            nameArabic = "التغابن",
            nameEnglish = "At-Taghabun",
            nameBangla = "আত-তাগাবুন",
            versesCount = 18,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/064.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("064  SURA  ATTAGABOON.mp3"),
            banglaSummary = "লাভ-ক্ষতির দিন; সম্পদ ও সন্তানের অতিরিক্ত মোহের পরীক্ষা।"
        ),
        QuranSurah(
            number = 65,
            nameArabic = "الطلاق",
            nameEnglish = "At-Talaq",
            nameBangla = "আত-ত্বালাক",
            versesCount = 12,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/065.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("065 SURA  ATTALAK.mp3"),
            banglaSummary = "তাকওয়ার বরকত: অপ্রত্যাশিত উৎস থেকে রিজিক প্রাপ্তি।"
        ),
        QuranSurah(
            number = 66,
            nameArabic = "التحريم",
            nameEnglish = "At-Tahrim",
            nameBangla = "আত-তাহরীম",
            versesCount = 12,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/066.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("066 SURA  ATTAHRIM.mp3"),
            banglaSummary = "তওবায়ে নাসুহা (খাঁটি তওবা) ও নিজের পরিবারকে বাঁচানো।"
        ),
        QuranSurah(
            number = 67,
            nameArabic = "الملك",
            nameEnglish = "Al-Mulk",
            nameBangla = "আল-মুলক",
            versesCount = 30,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/067.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("067 SURA AL MULK.mp3"),
            banglaSummary = "কবরের আজাব থেকে মুক্তিদানকারী মহা মর্যাদাপূর্ণ সূরা।"
        ),
        QuranSurah(
            number = 68,
            nameArabic = "القلم",
            nameEnglish = "Al-Qalam",
            nameBangla = "আল-কলম",
            versesCount = 52,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/068.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("068 SURA AL KALAM.mp3"),
            banglaSummary = "কলমের শপথ ও রাসূল (সা.)-এর চরিত্রের সর্বোত্তম ভূয়সী প্রশংসা।"
        ),
        QuranSurah(
            number = 69,
            nameArabic = "الحاقة",
            nameEnglish = "Al-Haqqah",
            nameBangla = "আল-হাক্কাহ",
            versesCount = 52,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/069.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("069 SURA AL HAKKA.mp3"),
            banglaSummary = "অনিবার্য সত্য কিয়ামত ও ডানহাতে আমলনামা পাওয়ার আনন্দ।"
        ),
        QuranSurah(
            number = 70,
            nameArabic = "المعارج",
            nameEnglish = "Al-Maarij",
            nameBangla = "আল-মাআরিজ",
            versesCount = 44,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/070.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("070 SURA AL MAAREZ.mp3"),
            banglaSummary = "মানুষের অস্থির চিত্তবৃত্তি ও সালাতে নিষ্ঠাবানদের মুক্তি।"
        ),
        QuranSurah(
            number = 71,
            nameArabic = "نوح",
            nameEnglish = "Nuh",
            nameBangla = "নূহ",
            versesCount = 28,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/071.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("071 SURA  NOAH.mp3"),
            banglaSummary = "ইস্তিগফারের বরকত: পাপমুক্তি, বৃষ্টি ও সন্তানের প্রাচুর্য।"
        ),
        QuranSurah(
            number = 72,
            nameArabic = "الجن",
            nameEnglish = "Al-Jinn",
            nameBangla = "আল-জিন",
            versesCount = 28,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/072.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("072  SURA  AL JINN.mp3"),
            banglaSummary = "জিন জাতির কুরআন শ্রবণ ও সত্য গ্রহণের বিবরণ।"
        ),
        QuranSurah(
            number = 73,
            nameArabic = "المزمل",
            nameEnglish = "Al-Muzzammil",
            nameBangla = "আল-মুযযাম্মিল",
            versesCount = 20,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/073.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("073 SURA AL MUZZAMMIL.mp3"),
            banglaSummary = "তাহাজ্জুদের নামাজ ও গভীর রাতে ধীরস্থির তিলাওয়াত।"
        ),
        QuranSurah(
            number = 74,
            nameArabic = "المدثر",
            nameEnglish = "Al-Muddathir",
            nameBangla = "আল-মুদ্দাসসির",
            versesCount = 56,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/074.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("074 SURA AL MUDDATHTHIR.mp3"),
            banglaSummary = "আত্মশুদ্ধি, পরিচ্ছন্নতা এবং পাপ কাজ থেকে দূরে থাকা।"
        ),
        QuranSurah(
            number = 75,
            nameArabic = "القيامة",
            nameEnglish = "Al-Qiyamah",
            nameBangla = "আল-কিয়ামাহ",
            versesCount = 40,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/075.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("075 SURA AL QIYAMA.mp3"),
            banglaSummary = "বিবেকবান অন্তরের দংশন ও পরকালের হিসাবের দিন।"
        ),
        QuranSurah(
            number = 76,
            nameArabic = "الإنسان",
            nameEnglish = "Al-Insan",
            nameBangla = "আল-ইনসান / আদ-দাহর",
            versesCount = 31,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/076.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("076 SURA ADDAHOR.mp3"),
            banglaSummary = "মানুষের সৃষ্টি ও জান্নাতীদের অফুরন্ত আরাম ও নেয়ামত।"
        ),
        QuranSurah(
            number = 77,
            nameArabic = "المرسلات",
            nameEnglish = "Al-Mursalat",
            nameBangla = "আল-মুরসালাত",
            versesCount = 50,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/077.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("077 SURA AL MURSALAT.mp3"),
            banglaSummary = "সত্য অস্বীকারকারীদের চরম সতর্কবার্তা।"
        ),
        QuranSurah(
            number = 78,
            nameArabic = "النبأ",
            nameEnglish = "An-Naba",
            nameBangla = "আন-নাবা",
            versesCount = 40,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/078.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("078 SURA AN NABA.mp3"),
            banglaSummary = "কিয়ামতের মহাবার্তা ও তওবা না করার গভীর আফসোস।"
        ),
        QuranSurah(
            number = 79,
            nameArabic = "النازعات",
            nameEnglish = "An-Naziat",
            nameBangla = "আন-নাযিআত",
            versesCount = 46,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/079.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("079  SURA  ANNAZIAT.mp3"),
            banglaSummary = "প্রাণ হরণকারী ফেরেশতা ও কুপ্রবৃত্তি নিয়ন্ত্রণের জান্নাত।"
        ),
        QuranSurah(
            number = 80,
            nameArabic = "عبس",
            nameEnglish = "Abasa",
            nameBangla = "আবাসা",
            versesCount = 42,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/080.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("080 SURA ABASA.mp3"),
            banglaSummary = "অন্ধ সাহাবীর ঘটনা ও বিনম্র আন্তরিকতার মর্যাদা।"
        ),
        QuranSurah(
            number = 81,
            nameArabic = "التكوير",
            nameEnglish = "At-Takwir",
            nameBangla = "আত-তাকভীর",
            versesCount = 29,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/081.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("081 SURA  AT TAKWIR.mp3"),
            banglaSummary = "সূর্যের আলোহীন হওয়া ও কিয়ামতের আকাশ চূর্ণবিচূর্ণ দৃশ্য।"
        ),
        QuranSurah(
            number = 82,
            nameArabic = "الانفطار",
            nameEnglish = "Al-Infitar",
            nameBangla = "আল-ইনফিতার",
            versesCount = 19,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/082.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("082 SURA   AL INFITOR.mp3"),
            banglaSummary = "হে মানুষ! কোন জিনিস তোমাকে তোমার মহান রব থেকে গাফেল করল?"
        ),
        QuranSurah(
            number = 83,
            nameArabic = "المطففين",
            nameEnglish = "Al-Mutaffifin",
            nameBangla = "আল-মুতাফফিফীন",
            versesCount = 36,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/083.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("083  SURA   AL MUTAFFIFIN.mp3"),
            banglaSummary = "ওজনে কম দেওয়া ও অন্তরে পাপের দাগ পড়ার সাবধানবাণী।"
        ),
        QuranSurah(
            number = 84,
            nameArabic = "الانشقاق",
            nameEnglish = "Al-Inshiqaq",
            nameBangla = "আল-ইনশিকাক",
            versesCount = 25,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/084.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("084  SURA   AL  INSHIQAQ.mp3"),
            banglaSummary = "আল্লাহর সান্নিধ্যে প্রত্যাবর্তনের সহজ হিসাব।"
        ),
        QuranSurah(
            number = 85,
            nameArabic = "البروج",
            nameEnglish = "Al-Buruj",
            nameBangla = "আল-বুরুজ",
            versesCount = 22,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/085.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("085  SURA   AL BURUJ.mp3"),
            banglaSummary = "অগ্নিগহ্বরে নিক্ষিপ্ত অবিচল ঈমানদারদের মহা মর্যাদা।"
        ),
        QuranSurah(
            number = 86,
            nameArabic = "الطارق",
            nameEnglish = "At-Tariq",
            nameBangla = "আত-ত্বারিক",
            versesCount = 17,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/086.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("086 SURA   ATTARIQ.mp3"),
            banglaSummary = "প্রত্যেক মানুষের ওপর রক্ষক ফেরেশতা ও গোপন ভেদ প্রকাশের দিন।"
        ),
        QuranSurah(
            number = 87,
            nameArabic = "الأعلى",
            nameEnglish = "Al-Ala",
            nameBangla = "আল-আলা",
            versesCount = 19,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/087.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("087  SURA   AL A' LA.mp3"),
            banglaSummary = "সর্বোচ্চ রবের তাসবীহ ও যে নিজেকে পবিত্র করল সে সফল।"
        ),
        QuranSurah(
            number = 88,
            nameArabic = "الغاشية",
            nameEnglish = "Al-Ghashiyah",
            nameBangla = "আল-গাশিয়াহ",
            versesCount = 26,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/088.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("088  SURA   AL  GHASHIYA.mp3"),
            banglaSummary = "কিয়ামতের অন্ধকার ও জান্নাতীদের পরম তৃপ্ত মুখচ্ছবি।"
        ),
        QuranSurah(
            number = 89,
            nameArabic = "الفجر",
            nameEnglish = "Al-Fajr",
            nameBangla = "আল-ফজর",
            versesCount = 30,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/089.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("089 SURA   AL  FAJAR.mp3"),
            banglaSummary = "জিলহজের ১০ রাত ও প্রশান্ত আত্মার জান্নাতে প্রবেশ।"
        ),
        QuranSurah(
            number = 90,
            nameArabic = "البلد",
            nameEnglish = "Al-Balad",
            nameBangla = "আল-বালাদ",
            versesCount = 20,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/090.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("090  SURA   AL  BALAD.mp3"),
            banglaSummary = "কঠিন ঘাঁটি অতিক্রম: ক্ষুধার্ত ও এতিমকে খাদ্যদান।"
        ),
        QuranSurah(
            number = 91,
            nameArabic = "الشمس",
            nameEnglish = "Ash-Shams",
            nameBangla = "আশ-শামস",
            versesCount = 15,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/091.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("091 SURA   AL SHAMS.mp3"),
            banglaSummary = "যে অন্তরকে পবিত্র করল সে সফল, আর যে কলুষিত করল সে ব্যর্থ।"
        ),
        QuranSurah(
            number = 92,
            nameArabic = "الليل",
            nameEnglish = "Al-Layl",
            nameBangla = "আল-লাইল",
            versesCount = 21,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/092.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("092  SURA   AL LAYL.mp3"),
            banglaSummary = "দানশীল ও মুত্তাকির পথ আল্লাহ সহজ করে দেন।"
        ),
        QuranSurah(
            number = 93,
            nameArabic = "الضحى",
            nameEnglish = "Ad-Duha",
            nameBangla = "আদ-দুহা",
            versesCount = 11,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/093.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("093  SURA   AL DUHA.mp3"),
            banglaSummary = "বিষণ্ণতা মুক্তির মহৌষধ; তোমার ভবিষ্যৎ অতীতের চেয়ে উত্তম।"
        ),
        QuranSurah(
            number = 94,
            nameArabic = "الشرح",
            nameEnglish = "Ash-Sharh",
            nameBangla = "আল-ইনশিরাহ",
            versesCount = 8,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/094.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("094 SURA   AL  INSHIRAH.mp3"),
            banglaSummary = "নিশ্চয় কষ্টের সাথেই আছে অফুরন্ত স্বস্তি ও মুক্তি।"
        ),
        QuranSurah(
            number = 95,
            nameArabic = "التين",
            nameEnglish = "At-Tin",
            nameBangla = "আত-তীন",
            versesCount = 8,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/095.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("095 SURA   AL TIN.mp3"),
            banglaSummary = "মানুষকে সৃষ্টির সুন্দরতম অবয়বে সৃষ্টি করা হয়েছে।"
        ),
        QuranSurah(
            number = 96,
            nameArabic = "العلق",
            nameEnglish = "Al-Alaq",
            nameBangla = "আল-আলাক",
            versesCount = 19,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/096.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("096 SURA   AL ALAQ.mp3"),
            banglaSummary = "প্রথম ওহী; পড় তোমার রবের নামে এবং সেজদা করে কাছে আস।"
        ),
        QuranSurah(
            number = 97,
            nameArabic = "القدر",
            nameEnglish = "Al-Qadr",
            nameBangla = "আল-ক্বাদর",
            versesCount = 5,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/097.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("097  SURA   AL  QADAR.mp3"),
            banglaSummary = "হাজার মাসের চেয়ে শ্রেষ্ঠ মর্যাদাময় মহিমান্বিত কদরের রাত।"
        ),
        QuranSurah(
            number = 98,
            nameArabic = "البينة",
            nameEnglish = "Al-Bayyinah",
            nameBangla = "আল-বাইয়িনাহ",
            versesCount = 8,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/098.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("098  SURA   AL  BAYYINA.mp3"),
            banglaSummary = "স্পষ্ট প্রমাণ ও খাঁটি মনে একনিষ্ঠ ইবাদতের নির্দেশ।"
        ),
        QuranSurah(
            number = 99,
            nameArabic = "الزلزلة",
            nameEnglish = "Az-Zalzalah",
            nameBangla = "আয-যালযালাহ",
            versesCount = 8,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/099.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("099  SURA   AL ZALZAL.mp3"),
            banglaSummary = "অণু পরিমাণ ভালো কাজ বা খারাপ কাজের ফল মানুষ দেখতে পাবে।"
        ),
        QuranSurah(
            number = 100,
            nameArabic = "العاديات",
            nameEnglish = "Al-Adiyat",
            nameBangla = "আল-আদিয়াত",
            versesCount = 11,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/100.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("100  SURA   AL   ADIYAT.mp3"),
            banglaSummary = "যুদ্ধক্ষেত্রের ধাবমান অশ্ব ও মানুষের অকৃতজ্ঞতা।"
        ),
        QuranSurah(
            number = 101,
            nameArabic = "القارعة",
            nameEnglish = "Al-Qariah",
            nameBangla = "আল-কারিয়াহ",
            versesCount = 11,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/101.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("101  SURA   AL   QARIA.mp3"),
            banglaSummary = "কিয়ামতের মহাবিপদ ও পাল্লা ভারী হওয়ার চিরস্থায়ী সুখ।"
        ),
        QuranSurah(
            number = 102,
            nameArabic = "التكاثر",
            nameEnglish = "At-Takathur",
            nameBangla = "আত-তাকাসুর",
            versesCount = 8,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/102.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("102  SURA   AL   TAKATHUR.mp3"),
            banglaSummary = "অতিরিক্ত ধনদৌলতের প্রতিযোগিতা মানুষকে ভুলিয়ে রাখে।"
        ),
        QuranSurah(
            number = 103,
            nameArabic = "العصر",
            nameEnglish = "Al-Asr",
            nameBangla = "আল-আসর",
            versesCount = 3,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/103.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("103  SURA   AL  ASR.mp3"),
            banglaSummary = "সময়ের শপথ; ঈমান, সৎকাজ ও ধৈর্যের উপদেশ ছাড়া সকল মানুষ ক্ষতিগ্রস্ত।"
        ),
        QuranSurah(
            number = 104,
            nameArabic = "الهمزة",
            nameEnglish = "Al-Humazah",
            nameBangla = "আল-হুমাযাহ",
            versesCount = 9,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/104.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("104  SURA   AL HUMAZA.mp3"),
            banglaSummary = "পরনিন্দা ও সম্পদ জমিয়ে অহংকারকারীদের ভয়াবহ পরিণতি।"
        ),
        QuranSurah(
            number = 105,
            nameArabic = "الفيل",
            nameEnglish = "Al-Fil",
            nameBangla = "আল-ফীল",
            versesCount = 5,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/105.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("105  SURA   AL FIL.mp3"),
            banglaSummary = "আবরাহার হস্তীবাহিনী ধ্বংস ও কাবা ঘরের পবিত্র অলৌকিক সুরক্ষা।"
        ),
        QuranSurah(
            number = 106,
            nameArabic = "قريش",
            nameEnglish = "Quraysh",
            nameBangla = "কুরাইশ",
            versesCount = 4,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/106.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("106  SURA  QURAYSH.mp3"),
            banglaSummary = "আল্লাহর ঘরের রবের ইবাদত যিনি ক্ষুধায় আহার ও ভয় থেকে নিরাপত্তা দেন।"
        ),
        QuranSurah(
            number = 107,
            nameArabic = "الماعون",
            nameEnglish = "Al-Maun",
            nameBangla = "আল-মাউন",
            versesCount = 7,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/107.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("107  SURA  AL MA' UN.mp3"),
            banglaSummary = "লোক দেখানো নামাজি ও এতিম-মিসকিনকে অবহেলাকারীদের ধিক্কার।"
        ),
        QuranSurah(
            number = 108,
            nameArabic = "الكوثر",
            nameEnglish = "Al-Kawthar",
            nameBangla = "আল-কাউসার",
            versesCount = 3,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/108.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("108  SURA  AL KAWSHAR.mp3"),
            banglaSummary = "রাসূল (সা.)-কে হাউজে কাউসারের সুসংবাদ ও শত্রুর নির্বংশ হওয়া।"
        ),
        QuranSurah(
            number = 109,
            nameArabic = "الكافرون",
            nameEnglish = "Al-Kafirun",
            nameBangla = "আল-কাফিরুন",
            versesCount = 6,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/109.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("109  SURA  AL KAFIRUN.mp3"),
            banglaSummary = "শিরকমুক্ত খাঁটি তাওহীদ; তোমাদের দ্বীন তোমাদের, আমার দ্বীন আমার।"
        ),
        QuranSurah(
            number = 110,
            nameArabic = "النصر",
            nameEnglish = "An-Nasr",
            nameBangla = "আন-নাসর",
            versesCount = 3,
            revelationType = "মাদানী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/110.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("110  SURA  AL NASR.mp3"),
            banglaSummary = "মক্কা বিজয়, দলে দলে ইসলাম গ্রহণ ও আল্লাহর প্রশংসা ও ইস্তিগফার।"
        ),
        QuranSurah(
            number = 111,
            nameArabic = "المسد",
            nameEnglish = "Al-Masad",
            nameBangla = "আল-মাসাদ / লাহাব",
            versesCount = 5,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/111.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("111  SURA  LAHAB.mp3"),
            banglaSummary = "নবীর চরম শত্রু আবু লাহাব ও তার স্ত্রীর চূড়ান্ত ধ্বংস।"
        ),
        QuranSurah(
            number = 112,
            nameArabic = "الإخلاص",
            nameEnglish = "Al-Ikhlas",
            nameBangla = "আল-ইখলাস",
            versesCount = 4,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/112.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("112  SURA  AL IKHLAS.mp3"),
            banglaSummary = "কুরআনের এক-তৃতীয়াংশের সমান; আল্লাহর নিরঙ্কুশ একত্ববাদের ঘোষণা।"
        ),
        QuranSurah(
            number = 113,
            nameArabic = "الفلق",
            nameEnglish = "Al-Falaq",
            nameBangla = "আল-ফালাক",
            versesCount = 5,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/113.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("113   SURA  AL FALAQ.mp3"),
            banglaSummary = "হিংসুক ও জাদুকরের অনিষ্ট থেকে প্রভাত-রবের কাছে আশ্রয় প্রার্থনা।"
        ),
        QuranSurah(
            number = 114,
            nameArabic = "الناس",
            nameEnglish = "An-Nas",
            nameBangla = "আন-নাস",
            versesCount = 6,
            revelationType = "মক্কী",
            audioArabicUrl = "https://server8.mp3quran.net/afs/114.mp3",
            audioBanglaUrl = buildBanglaArchiveUrl("114  SURA AL NAS.mp3"),
            banglaSummary = "শয়তানের অন্তরের অদৃশ্য কুমন্ত্রণা থেকে মানুষের প্রতিপালকের কাছে আশ্রয়।"
        )
    )

    val hadithBooks: List<HadithBook> = listOf(
        HadithBook(
            id = "bukhari",
            titleBangla = "সহীহুল বুখারী",
            titleArabic = "صحيح البخاري",
            author = "ইমাম মুহাম্মদ ইবনে ইসমাইল আল-বুখারী (রহ.)",
            description = "কুরআনুল কারীমের পর পৃথিবীর বিশুদ্ধতম গ্রন্থ। এতে রাসূলুল্লাহ (সা.)-এর পবিত্র সুন্নাহ ও চরিত্র সংরক্ষিত রয়েছে।",
            hadiths = listOf(
                HadithItem(
                    id = "bukhari_1",
                    bookName = "সহীহ বুখারী",
                    chapterName = "ওহীর সূচনা অধ্যায়",
                    hadithNumber = "হাদিস ১",
                    narrator = "উমর ইবনুল খাত্তাব (রা.)",
                    arabicText = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى",
                    banglaTranslation = "নিশ্চয়ই সমস্ত আমল নিয়তের ওপর নির্ভরশীল। আর প্রত্যেক ব্যক্তি যা নিয়ত করবে তাই সে পাবে।",
                    significance = "মোবাইল ব্যবহার ও জীবনের প্রতিটি পদক্ষেপে আল্লাহর সন্তুষ্টির নিয়ত রাখুন।"
                ),
                HadithItem(
                    id = "bukhari_9",
                    bookName = "সহীহ বুখারী",
                    chapterName = "কিতাবুল ঈমান",
                    hadithNumber = "হাদিস ৯",
                    narrator = "আবু হুরায়রা (রা.)",
                    arabicText = "الْحَيَاءُ شُعْبَةٌ مِنَ الإِيمَانِ",
                    banglaTranslation = "লজ্জাশীলতা হলো ঈমানের একটি অপরিহার্য শাখা।",
                    significance = "লজ্জাশীল মানুষ কখনো গোপনে বা একাকী অশ্লীল ও হারাম কিছুতে আসক্ত হতে পারে না।"
                ),
                HadithItem(
                    id = "bukhari_6474",
                    bookName = "সহীহ বুখারী",
                    chapterName = "কিতাবুর রিকাক (মন গলানো উপদেশ)",
                    hadithNumber = "হাদিস ৬৪৭৪",
                    narrator = "সাহল ইবনে সা'দ (রা.)",
                    arabicText = "مَنْ يَضْمَنْ لِي مَا بَيْنَ لَحْيَيْهِ وَمَا بَيْنَ رِجْلَيْهِ أَضْمَنْ لَهُ الْجَنَّةَ",
                    banglaTranslation = "রাসূলুল্লাহ (সা.) বলেছেন: যে ব্যক্তি তার দুই চোয়ালের মধ্যবর্তী অঙ্গ (জিহ্বা) এবং দুই উরুর মধ্যবর্তী অঙ্গের (লজ্জাস্থান) জামিনদার হবে, আমি তার জন্য জান্নাতের জামিনদার হবো।",
                    significance = "লজ্জাস্থান ও দৃষ্টির সুরক্ষাই একজন মুমিনের জান্নাত নিশ্চিত করে।"
                ),
                HadithItem(
                    id = "bukhari_52",
                    bookName = "সহীহ বুখারী",
                    chapterName = "কিতাবুল ঈমান",
                    hadithNumber = "হাদিস ৫২",
                    narrator = "নুমান ইবনে বাশীর (রা.)",
                    arabicText = "أَلاَ وَإِنَّ فِي الْجَسَدِ مُضْغَةً إِذَا صَلَحَتْ صَلَحَ الْجَسَدُ كُلُّهُ، وَإِذَا فَسَدَتْ فَسَدَ الْجَسَدُ كُلُّهُ، أَلاَ وَهِيَ الْقَلْبُ",
                    banglaTranslation = "জেনে রেখো! মানবদেহে একটি মাংসের টুকরো আছে; তা যখন পরিশুদ্ধ থাকে সমগ্র দেহ পরিশুদ্ধ থাকে, আর তা যখন কলুষিত হয় সমগ্র দেহ কলুষিত হয়ে যায়। জেনে রেখো, তা হলো অন্তর (কলব)।",
                    significance = "চোখের কুদৃষ্টি সরাসরি অন্তরকে বিষাক্ত করে। দৃষ্টি পাহারা দিন, অন্তর সুস্থ থাকবে।"
                ),
                HadithItem(
                    id = "bukhari_1145",
                    bookName = "সহীহ বুখারী",
                    chapterName = "তাহাজ্জুদ ও রাতের সালাত অধ্যায়",
                    hadithNumber = "হাদিস ১১৪৫",
                    narrator = "আবু হুরায়রা (রা.)",
                    arabicText = "يَنْزِلُ رَبُّنَا تَبَارَكَ وَتَعَالَى كُلَّ لَيْلَةٍ إِلَى السَّمَاءِ الدُّنْيَا حِينَ يَبْقَى ثُلُثُ اللَّيْلِ الآخِرُ يَقُولُ: مَنْ يَدْعُونِي فَأَسْتَجِيبَ لَهُ",
                    banglaTranslation = "আমাদের বরকতময় প্রতিপালক প্রতি রাতের শেষ তৃতীয়াংশে প্রথম আকাশে অবতরণ করেন এবং ঘোষণা করেন: কে আছ যে আমাকে ডাকবে, আমি তার ডাকে সাড়া দেবো? কে আছ যে আমার কাছে কিছু চাইবে, আমি তাকে তা দান করবো? কে আছ যে আমার কাছে ক্ষমা প্রার্থনা করবে, আমি তাকে ক্ষমা করবো?",
                    significance = "গভীর রাতে ফোন বন্ধ রেখে সেজদায় অশ্রু ফেলে নিজের জন্য আল্লাহর কাছে মাগফিরাত ও মুক্তি চান।"
                ),
                HadithItem(
                    id = "bukhari_6412",
                    bookName = "সহীহ বুখারী",
                    chapterName = "রিকাক (মন নরমকারী উপদেশ)",
                    hadithNumber = "হাদিস ৬৪১২",
                    narrator = "ইবনে আব্বাস (রা.)",
                    arabicText = "نِعْمَتَانِ مَغْبُونٌ فِيهِمَا كَثِيرٌ مِنَ النَّاسِ: الصِّحَّةُ وَالْفَرَاغُ",
                    banglaTranslation = "দুটি নিয়ামত এমন রয়েছে, যার ব্যাপারে অধিকাংশ মানুষই ধোঁকার মধ্যে থাকে (মূল্যায়ন করতে পারে না): স্বাস্থ্য এবং অবসর সময়।",
                    significance = "ফোনের অপ্রয়োজনীয় স্ক্রলিংয়ে অমূল্য সময় ও যৌবনের শক্তি অপচয় করা থেকে নিজেকে বিরত রাখুন।"
                ),
                HadithItem(
                    id = "bukhari_6018",
                    bookName = "সহীহ বুখারী",
                    chapterName = "কিতাবুল আদব (শিষ্টাচার অধ্যায়)",
                    hadithNumber = "হাদিস ৬০১৮",
                    narrator = "আবু হুরায়রা (রা.)",
                    arabicText = "مَنْ كَانَ يُؤْمِنُ بِاللَّهِ وَالْيَوْمِ الآخِرِ فَلْيَقُلْ خَيْرًا أَوْ لِيَصْمُتْ",
                    banglaTranslation = "যে ব্যক্তি আল্লাহ এবং শেষ দিবসের প্রতি ঈমান রাখে, সে যেন ভালো কথা বলে অথবা নীরব থাকে।",
                    significance = "সোশ্যাল মিডিয়া ও মেসেজিংয়ে ভালো মন্তব্য করুন, অন্যথায় নীরবতা অবলম্বন করুন।"
                )
            )
        ),
        HadithBook(
            id = "muslim",
            titleBangla = "সহীহ মুসলিম",
            titleArabic = "صحيح مسلم",
            author = "ইমাম মুসলিম ইবনুল হাজ্জাজ (রহ.)",
            description = "বিশুদ্ধ হাদিস সংকলনের অন্যতম শ্রেষ্ঠ রত্ন। এর বিন্যাস ও প্রাসঙ্গিক নির্দেশনা মুমিনের পাথেয়।",
            hadiths = listOf(
                HadithItem(
                    id = "muslim_2749",
                    bookName = "সহীহ মুসলিম",
                    chapterName = "কিতাবুত তাওবাহ (তওবা অধ্যায়)",
                    hadithNumber = "হাদিস ২৭৪৯",
                    narrator = "আনাস ইবনে মালিক (রা.)",
                    arabicText = "لَلَّهُ أَشَدُّ فَرَحًا بِتَوْبَةِ عَبْدِهِ حِينَ يَتُوبُ إِلَيْهِ مِنْ أَحَدِكُمْ كَانَ عَلَى رَاحِلَتِهِ بِأَرْضِ فَلاَةٍ",
                    banglaTranslation = "বান্দা যখন তওবা করে আল্লাহর দিকে ফিরে আসে, আল্লাহ তার তওবায় মরুভূমিতে হারানো উট ও খাদ্যসামগ্রী ফিরে পাওয়া পথিকের চেয়েও অধিক আনন্দিত হন।",
                    significance = "ভুল হয়ে গেলে কখনো নিরাশ হবেন না। রব আপনার তওবার অপেক্ষায় আছেন।"
                ),
                HadithItem(
                    id = "muslim_2548",
                    bookName = "সহীহ মুসলিম",
                    chapterName = "সদ্ব্যবহার ও আত্মীয়তা অধ্যায়",
                    hadithNumber = "হাদিস ২৫৪৮",
                    narrator = "আবু হুরায়রা (রা.)",
                    arabicText = "رَغِمَ أَنْفُ، ثُمَّ رَغِمَ أَنْفُ، ثُمَّ رَغِمَ أَنْفُ مَنْ أَدْرَكَ أَبَوَيْهِ عِنْدَ الْكِبَرِ أَحَدَهُمَا أَوْ كِلَيْهِمَا فَلَمْ يَدْخُلِ الْجَنَّةَ",
                    banglaTranslation = "নাক ধূলিধূসরিত হোক, অতঃপর নাক ধূলিধূসরিত হোক, অতঃপর তার নাক ধূলিধূসরিত হোক—যে ব্যক্তি পিতা-মাতার যেকোনো একজনকে অথবা উভয়কে বৃদ্ধাবস্থায় পেল অথচ জান্নাতে প্রবেশ করতে পারল না।",
                    significance = "পিতা-মাতার সেবা ও দোয়া আপনার জীবনে সর্বপ্রকার কুপ্রবৃত্তি থেকে বাঁচার ঢাল।"
                ),
                HadithItem(
                    id = "muslim_216",
                    bookName = "সহীহ মুসলিম",
                    chapterName = "কিতাবুল ঈমান",
                    hadithNumber = "হাদিস ২১৬",
                    narrator = "আবু যার (রা.)",
                    arabicText = "عَلَيْكَ بِتَقْوَى اللَّهِ فَإِنَّهُ رَأْسُ الأَمْرِ كُلِّهِ",
                    banglaTranslation = "তোমার ওপর আবশ্যক হলো আল্লাহকে ভয় করা (তাকওয়া অবলম্বন করা); কারণ এটিই সকল কাজের মূল ভিত্তি।",
                    significance = "নির্জনে এবং জনসমক্ষে আল্লাহকে ভয় করাই প্রকৃত মুত্তাকির পরিচয়।"
                ),
                HadithItem(
                    id = "muslim_2159",
                    bookName = "সহীহ মুসলিম",
                    chapterName = "আদব ও কুদৃষ্টি নিবারণ অধ্যায়",
                    hadithNumber = "হাদিস ২১৫৯",
                    narrator = "জারীর ইবনে আবদুল্লাহ (রা.)",
                    arabicText = "سَأَلْتُ رَسُولَ اللَّهِ صلى الله عليه وسلم عَنْ نَظَرِ الْفَجْأَةِ فَأَمَرَنِي أَنْ أَصْرِفَ بَصَرِي",
                    banglaTranslation = "আমি রাসূলুল্লাহ (সা.)-কে আকস্মিক দৃষ্টি (অনিচ্ছাকৃতভাবে কোনো বেগানা নারীর প্রতি দৃষ্টি পড়া) সম্পর্কে জিজ্ঞেস করলাম। তিনি আমাকে নির্দেশ দিলেন আমি যেন তৎক্ষণাৎ দৃষ্টি ফিরিয়ে নেই।",
                    significance = "প্রথম অনিচ্ছাকৃত দৃষ্টিতে কোনো পাপ নেই, কিন্তু দ্বিতীয়বার ইচ্ছাকৃত দৃষ্টি ফেলা শয়তানের বিষাক্ত তীর। সাথে সাথে স্ক্রিন বন্ধ করুন।"
                ),
                HadithItem(
                    id = "muslim_2564",
                    bookName = "সহীহ মুসলিম",
                    chapterName = "ভ্রাতৃত্ব ও তাকওয়া অধ্যায়",
                    hadithNumber = "হাদিস ২৫৬৪",
                    narrator = "আবু হুরায়রা (রা.)",
                    arabicText = "إِنَّ اللَّهَ لاَ يَنْظُرُ إِلَى صُوَرِكُمْ وَأَمْوَالِكُمْ، وَلَكِنْ يَنْظُرُ إِلَى قُلُوبِكُمْ وَأَعْمَالِكُمْ",
                    banglaTranslation = "নিশ্চয়ই আল্লাহ তোমাদের বাহ্যিক রূপ ও ধন-সম্পদের দিকে তাকান না, বরং তিনি তোমাদের অন্তর এবং তোমাদের আমলের দিকে তাকান।",
                    significance = "আল্লাহ আপনার অন্তরের নিয়ত ও পবিত্রতা দেখেন। অন্তরকে পাপের কালিমা থেকে মুক্ত রাখুন।"
                )
            )
        ),
        HadithBook(
            id = "tirmidhi",
            titleBangla = "জামে আত-তিরমিযী",
            titleArabic = "جامع الترمذي",
            author = "ইমাম আবু ঈসা মুহাম্মদ আত-তিরমিযী (রহ.)",
            description = "সুন্নাহ ও ফিকহের সুবিশাল আকর গ্রন্থ। এতে চারিত্রিক উৎকর্ষ ও আত্মনিয়ন্ত্রণের অনুপম শিক্ষা রয়েছে।",
            hadiths = listOf(
                HadithItem(
                    id = "tirmidhi_1081",
                    bookName = "জামে তিরমিযী",
                    chapterName = "বিবাহ ও যৌবন নিয়ন্ত্রণ অধ্যায়",
                    hadithNumber = "হাদিস ১০৮১",
                    narrator = "আবদুল্লাহ ইবনে মাসউদ (রা.)",
                    arabicText = "يَا مَعْشَرَ الشَّبَابِ مَنِ اسْتَطَاعَ مِنْكُمُ الْبَاءَةَ فَلْيَتَزَوَّجْ... وَمَنْ لَمْ يَسْتَطِعْ فَعَلَيْهِ بِالصَّوْمِ فَإِنَّهُ لَهُ وِجَاءٌ",
                    banglaTranslation = "হে যুবসমাজ! তোমাদের মধ্যে যার সামর্থ্য আছে সে যেন বিবাহ করে... আর যার সামর্থ্য নেই সে যেন সিয়াম (রোজা) পালন করে; কেননা রোজা তার কামভাব দমনকারী ঢালস্বরূপ।",
                    significance = "কুপ্রবৃত্তি ও আসক্তির তীব্রতা কমাতে নফল রোজা রাখা অন্যতম সেরা চিকিৎসা।"
                ),
                HadithItem(
                    id = "tirmidhi_2416",
                    bookName = "জামে তিরমিযী",
                    chapterName = "কেয়ামতের বিবরণ অধ্যায়",
                    hadithNumber = "হাদিস ২৪১৬",
                    narrator = "ইবনে মাসউদ (রা.)",
                    arabicText = "لاَ تَزُولُ قَدَمَا ابْنِ آدَمَ يَوْمَ الْقِيَامَةِ مِنْ عِنْدِ رَبِّهِ حَتَّى يُسْأَلَ عَنْ خَمْسٍ: عَنْ عُمُرِهِ فِيمَا أَفْنَاهُ، وَعَنْ شَبَابِهِ فِيمَا أَبْلاَهُ...",
                    banglaTranslation = "কেয়ামতের দিন পাঁচটি প্রশ্নের উত্তর না দেওয়া পর্যন্ত কোনো আদম সন্তানের পা একবিন্দু নড়তে পারবে না: ১. তার বয়স কীভাবে ব্যয় করেছে, ২. তার যৌবন কীভাবে অতিবাহিত করেছে, ৩. ধন-সম্পদ কোথা থেকে উপার্জন করেছে, ৪. কোন পথে তা ব্যয় করেছে, ৫. অর্জিত জ্ঞান অনুযায়ী কতটা আমল করেছে।",
                    significance = "যৌবনের রাতগুলো কীভাবে স্ক্রিনের সামনে কাটছে তার হিসাব আল্লাহর সামনে দিতে হবে।"
                ),
                HadithItem(
                    id = "tirmidhi_1987",
                    bookName = "জামে তিরমিযী",
                    chapterName = "সৎ চরিত্র ও শিষ্টাচার অধ্যায়",
                    hadithNumber = "হাদিস ১৯৮৭",
                    narrator = "আবু দারদা (রা.)",
                    arabicText = "مَا شَيْءٌ أَثْقَلُ فِي مِيزَانِ الْمُؤْمِنِ يَوْمَ الْقِيَامَةِ مِنْ خُلُقٍ حَسَنٍ",
                    banglaTranslation = "কেয়ামতের দিন মুমিনের দাঁড়িপাল্লায় সচ্চরিত্রের চেয়ে অধিক ভারী আর কোনো বস্তু হবে না।",
                    significance = "সুন্দর চরিত্র, বিনম্র ভাষা ও শালীন আচরণই ঈমানের সর্বোচ্চ অলংকার।"
                )
            )
        ),
        HadithBook(
            id = "abu_dawud",
            titleBangla = "সুনানে আবু দাউদ",
            titleArabic = "سنن أبي داود",
            author = "ইমাম আবু দাউদ সুলায়মান ইবনুল আশআস (রহ.)",
            description = "ফিকহ ও আহকামের শ্রেষ্ঠ হাদিস গ্রন্থ। দ্বীনের বিধিবিধান ও চরিত্র গঠনের স্পষ্ট দলিল।",
            hadiths = listOf(
                HadithItem(
                    id = "abudawud_4833",
                    bookName = "সুনানে আবু দাউদ",
                    chapterName = "বন্ধুত্ব ও সাহচর্য অধ্যায়",
                    hadithNumber = "হাদিস ৪৮৩৩",
                    narrator = "আবু হুরায়রা (রা.)",
                    arabicText = "الرَّجُلُ عَلَى دِينِ خَلِيلِهِ، فَلْيَنْظُرْ أَحَدُكُمْ مَنْ يُخَالِلُ",
                    banglaTranslation = "মানুষ তার ঘনিষ্ঠ বন্ধুর দ্বীন ও স্বভাব দ্বারা প্রভাবিত হয়। অতএব তোমাদের প্রত্যেকে যেন লক্ষ্য রাখে সে কার সাথে বন্ধুত্ব স্থাপন করছে।",
                    significance = "অনলাইনে ও অফলাইনে সৎ ও আল্লাহভীরু মানুষের সঙ্গ গ্রহণ করুন।"
                ),
                HadithItem(
                    id = "abudawud_4782",
                    bookName = "সুনানে আবু দাউদ",
                    chapterName = "রাগ নিয়ন্ত্রণ অধ্যায়",
                    hadithNumber = "হাদিস ৪৭৮২",
                    narrator = "আতিয়্যাহ আস-সাদী (রা.)",
                    arabicText = "إِنَّ الْغَضَبَ مِنَ الشَّيْطَانِ، وَإِنَّ الشَّيْطَانَ خُلِقَ مِنَ النَّارِ، وَإِنَّمَا تُطْفَأُ النَّارُ بِالْمَاءِ، فَإِذَا غَضِبَ أَحَدُكُمْ فَلْيَتَوَضَّأْ",
                    banglaTranslation = "নিশ্চয়ই রাগ শয়তানের পক্ষ থেকে আসে। আর শয়তানকে আগুন দিয়ে সৃষ্টি করা হয়েছে। পানি দিয়ে আগুন নেভানো হয়। সুতরাং তোমাদের কারো রাগ আসলে সে যেন অজু করে নেয়।",
                    significance = "পাপের উত্তেজনা কিংবা অস্থিরতা বোধ করলে সাথে সাথে ঠাণ্ডা পানিতে অজু করে দু'রাকাত সালাত আদায় করুন।"
                )
            )
        ),
        HadithBook(
            id = "riyad",
            titleBangla = "রিয়াযুস সালেহীন",
            titleArabic = "رياض الصالحين",
            author = "ইমাম আবু যাকারিয়া মুহিউদ্দীন আন-নববী (রহ.)",
            description = "সৎকর্মশীলদের জান্নাতী কানন। আত্মশুদ্ধি, আখলাক ও চরিত্র সংশোধনের অনুপম কিতাব।",
            hadiths = listOf(
                HadithItem(
                    id = "riyad_61",
                    bookName = "রিয়াযুস সালেহীন",
                    chapterName = "মুরাকাবা (আল্লাহর সান্নিধ্য ভাবনা)",
                    hadithNumber = "হাদিস ৬১",
                    narrator = "ইবনে আব্বাস (রা.)",
                    arabicText = "احْفَظِ اللَّهَ يَحْفَظْكَ، احْفَظِ اللَّهَ تَجِدْهُ تُجَاهَكَ",
                    banglaTranslation = "তুমি আল্লাহর হুকুমসমূহ হেফাজত করো, আল্লাহ তোমাকে সর্বাবস্থায় হেফাজত করবেন। তুমি আল্লাহকে স্মরণ রাখো, তুমি তাঁকে তোমার সামনেই পাবে।",
                    significance = "যখনই কোনো হারাম দেখার প্রলোভন আসবে, মনে রাখবেন আল্লাহ আপনাকে দেখছেন।"
                ),
                HadithItem(
                    id = "riyad_103",
                    bookName = "রিয়াযুস সালেহীন",
                    chapterName = "ধৈর্য ও আত্মনিয়ন্ত্রণ অধ্যায়",
                    hadithNumber = "হাদিস ১০৩",
                    narrator = "আবু হুরায়রা (রা.)",
                    arabicText = "لَيْسَ الشَّدِيدُ بِالصُّرَعَةِ، إِنَّمَا الشَّدِيدُ الَّذِي يَمْلِكُ نَفْسَهُ عِنْدَ الْغَضَبِ",
                    banglaTranslation = "কুস্তিতে জয়ী ব্যক্তি প্রকৃত বীর নয়; প্রকৃত বীর তো সেই ব্যক্তি যে উত্তেজনার মুহূর্তে নিজেকে নিয়ন্ত্রণে রাখতে পারে।",
                    significance = "পাপের প্রচণ্ড তাড়নার সময় নিজেকে রুখে দিতে পারাই প্রকৃত বীরত্ব।"
                ),
                HadithItem(
                    id = "riyad_23",
                    bookName = "রিয়াযুস সালেহীন",
                    chapterName = "ইস্তিগফার ও তওবা",
                    hadithNumber = "হাদিস ২৩",
                    narrator = "আগারা আল-মুযানী (রা.)",
                    arabicText = "إِنَّهُ لَيُغَانُ عَلَى قَلْبِي، وَإِنِّي لأَسْتَغْفِرُ اللَّهَ فِي الْيَوْمِ مِائَةَ مَرَّةٍ",
                    banglaTranslation = "রাসূলুল্লাহ (সা.) বলেছেন: নিশ্চয়ই আমার অন্তরের ওপরও কখনো আবরণ পড়ে, আর আমি প্রত্যহ আল্লাহর দরবারে একশত বার ক্ষমা প্রার্থনা (ইস্তিগফার) করি।",
                    significance = "নিয়মিত ইস্তিগফার অন্তরের ময়লা ও অন্ধকার দূর করে নূর বৃদ্ধি করে।"
                )
            )
        ),
        HadithBook(
            id = "nawawi_40",
            titleBangla = "ইমাম নববীর চল্লিশ হাদিস",
            titleArabic = "الأربعون النووية",
            author = "ইমাম মুহিউদ্দীন আন-নববী (রহ.)",
            description = "ইসলামের মৌলিক আকিদা, আমল ও চরিত্রের সারসংক্ষেপ। প্রতিটি মুসলিমের জন্য অত্যাবশ্যকীয় পাথেয়।",
            hadiths = listOf(
                HadithItem(
                    id = "nawawi_11",
                    bookName = "চল্লিশ হাদিস",
                    chapterName = "সন্দেহজনক বিষয় পরিহার",
                    hadithNumber = "হাদিস ১১",
                    narrator = "আল-হাসান ইবনে আলী (রা.)",
                    arabicText = "دَعْ مَا يَرِيبُكَ إِلَى مَا لاَ يَرِيبُكَ",
                    banglaTranslation = "যা তোমাকে সন্দেহে ফেলে তা পরিহার করে যা তোমাকে সন্দেহে ফেলে না তার দিকে ধাবিত হও।",
                    significance = "যে সমস্ত কনটেন্ট বা অ্যাপস দেখে মনে খটকা লাগে তা চিরতরে আনইনস্টল করুন।"
                ),
                HadithItem(
                    id = "nawawi_12",
                    bookName = "চল্লিশ হাদিস",
                    chapterName = "অনর্থক কাজ বর্জন",
                    hadithNumber = "হাদিস ১২",
                    narrator = "আবু হুরায়রা (রা.)",
                    arabicText = "مِنْ حُسْنِ إِسْلاَمِ الْمَرْءِ تَرْكُهُ مَا لاَ يَعْنِيهِ",
                    banglaTranslation = "মানুষের ইসলামের অন্যতম সৌন্দর্য হলো যা তার কোনো উপকারে আসে না তা বর্জন করা।",
                    significance = "অনর্থক শর্টস ও রিলস দেখা ছেড়ে দিলে অন্তর প্রশান্তিতে ভরে ওঠে।"
                ),
                HadithItem(
                    id = "nawawi_18",
                    bookName = "চল্লিশ হাদিস",
                    chapterName = "সর্বাবস্থায় তাকওয়া ও ভালো কাজের গুরুত্ব",
                    hadithNumber = "হাদিস ১৮",
                    narrator = "আবু যার (রা.)",
                    arabicText = "اتَّقِ اللَّهَ حَيْثُمَا كُنْتَ، وَأَتْبِعِ السَّيِّئَةَ الْحَسَنَةَ تَمْحُهَا، وَخَالِقِ النَّاسَ بِخُلُقٍ حَسَنٍ",
                    banglaTranslation = "তুমি যেখানেই থাকো না কেন আল্লাহকে ভয় করো। কোনো মন্দ কাজ হয়ে গেলে সাথে সাথেই একটি নেক কাজ করো, তা পূর্ববর্তী মন্দকে মুছে দেবে। আর মানুষের সাথে উত্তম আচরণ করো।",
                    significance = "একাকী গোপন রুমেও আল্লাহকে ভয় করুন; ভুল হলে সাথে সাথে দান বা সালাত আদায় করুন।"
                )
            )
        )
    )

    val islamicPoems: List<IslamicPoem> = listOf(
        IslamicPoem(
            id = "nazrul_1",
            title = "তোরা দেখে যা আমিনা মায়ের কোলে",
            poet = "কাজী নজরুল ইসলাম",
            theme = "নবীর শুভাগমন ও বিশ্বমানবতার মুক্তি",
            stanzas = listOf(
                "তোরা দেখে যা আমিনা মায়ের কোলে।\nমধু পূর্ণিমারই সেথা চাঁদ দোলে।\nযেন ঊষার কোলে রাঙা রবি দোলে।",
                "তাঁর নূরেরই রওশনীতে নিখিল ধরণী হাসে,\nআজ দিকে দিকে খুশির হাওয়া ভাসে।\nভুলে যা রে সকল ব্যথা, ওরে মানবদল,\nএলো ধরায় রহমত আর শান্তির সম্বল।",
                "পাপ-পঙ্কিল এই ধরায় নবীর পদধূলি,\nঘুচিয়ে দিল জাহেলিয়াত, সকল আঁধার তুলি।\nএসো হে মোমিন, পড়ো দরূদ তাঁরই তরে,\nযে নবী গো উম্মতের তরে সারা জীবন কাঁদে।"
            ),
            spiritualReflection = "প্রিয় নবী (সা.)-এর পবিত্র আদর্শ ও ভালোবাসা অন্তরে থাকলে পাপের অন্ধকার আপনাআপনি দূরীভূত হয়।"
        ),
        IslamicPoem(
            id = "nazrul_2",
            title = "মসজিদেরই পাশে আমায় কবর দিও ভাই",
            poet = "কাজী নজরুল ইসলাম",
            theme = "মৃত্যুচিন্তা ও আযানের সুর",
            stanzas = listOf(
                "মসজিদেরই পাশে আমায় কবর দিও ভাই,\nযেন গোরে থেকেও মোয়াজ্জিনের আযান শুনতে পাই।",
                "কত পরহেজগার যায় মসজিদে কত জিকির করে,\nসেই তাসবীহ পাঠের মধুর আওয়াজ আমার বুকে ধরে।\nআমার ভাঙা কবরে যেন রহমতের নূর ঝরে।",
                "পাপের বোঝা ভারি আমার, খোদা ক্ষমা করো,\nকবরের ঐ আঁধার ঘরে নূরের বাতি ধরো।\nতোমার রহম বিনে আমার কোনো গতি নাই।"
            ),
            spiritualReflection = "মৃত্যুর কথা স্মরণ মুমিনকে ক্ষণস্থায়ী গুনাহ ও অশ্লীল আসক্তি থেকে ফেরানোর মহৌষধ।"
        ),
        IslamicPoem(
            id = "nazrul_3",
            title = "হে নামাজী! আমার ঘরে নামাজ পড় আজ",
            poet = "কাজী নজরুল ইসলাম",
            theme = "বিনম্র সেজদা ও খোদার সান্নিধ্য",
            stanzas = listOf(
                "হে নামাজী! আমার ঘরে নামাজ পড় আজ,\nদিল-দরজা খুলে দিয়ে সারো সকল কাজ।",
                "যেই সেজদাতে কাঁপে খোদার আরশে আজিম হায়,\nসেই সেজদাটি লুটিয়ে পড়ো তাঁহারই চরণে গো তায়।\nসব অহংকার ধুয়ে ফেলে চোখের জলে স্নান করো ভাই।",
                "নামাজ তো নয় শুধু মাথা নোয়ানো মাটির তলে,\nনামাজ হলো রবের সাথে মিলন চোখের জলে।"
            ),
            spiritualReflection = "একাগ্রচিত্তে সেজদা দিলে অন্তর সকল অশোভন ও পঙ্কিল চিন্তা থেকে পরিশুদ্ধ হয়ে ওঠে।"
        ),
        IslamicPoem(
            id = "farrukh_1",
            title = "পাঞ্জেরী",
            poet = "ফররুখ আহমদ",
            theme = "ঈমানী জাগরণ ও ভোরের প্রত্যাশা",
            stanzas = listOf(
                "রাত পোহাবার কত দেরি পাঞ্জেরী?\nএখনো তোমার আসমান ভরা মেঘে?\nসেতারা হেথায় এখনো ওঠেনি জেগে?",
                "তুমি মাস্তুলে আমি দাঁড় টানি ভুলে,\nঅসীম কুয়াশা কাটে না যে কোনোকালে!\nকখন জাগিবে আলোকোজ্জ্বল ভোর?\nকখন টুটেবে এই আঁধারের ঘোর?",
                "সাহসের সাথে তোলো রে পাল,\nআসিতেছে সত্যের নব সুপ্রভাত কাল!\nঈমানী শক্তিতে বক্ষ বাঁধো ভাই,\nআঁধার সাগরে আর যেন ভয় নাই।"
            ),
            spiritualReflection = "পাপের কুয়াশা ভেদ করে তওবার ভোর আসবেই; শুধু প্রয়োজন অবিচল সংকল্প।"
        ),
        IslamicPoem(
            id = "al_mahmud_1",
            title = "আল্লাহর মহিমা ও তাওহীদ",
            poet = "আল মাহমুদ",
            theme = "স্রষ্টার নিখুঁত সৃষ্টি ও শুকরিয়া",
            stanzas = listOf(
                "তুমি রব্বুল আলামীন, মহাবিশ্বের একচ্ছত্র স্বামী,\nতোমারই কুদরতে ফোটে ফুল, আকাশ হয় নমি।",
                "গাছের পাতায় পাতায় লেখা তোমার নামের গান,\nনদীর কলতানে বাজে তোমারই অফুরন্ত দান।\nহে রহমান! আমাকে দাও তোমার পথের দিশা,\nআমার মনের সকল আঁধার করে দাও ফর্সা।",
                "তোমার কাছেই শেষ আশ্রয়, তোমার কাছেই ফেরা,\nতোমার রহমতের চাদরে মুমিন বান্দা ঘেরা।"
            ),
            spiritualReflection = "আল্লাহর সৃষ্টির দিকে তাকালে আত্মিক প্রশান্তি মেলে এবং অন্তরের অস্থিরতা কেটে যায়।"
        ),
        IslamicPoem(
            id = "nasheed_spirit_1",
            title = "মন রে আমার করিস না আর পাপের কারখানা",
            poet = "ঐতিহ্যবাহী আধ্যাত্মিক নাশীদ",
            theme = "আত্মশুদ্ধি ও তওবা",
            stanzas = listOf(
                "মন রে আমার করিস না আর পাপের কারখানা,\nএই দুনিয়া রবে না রে, সামনে পরোয়ানা।",
                "চোখের পলকে নিভে যাবে সাধের এই জীবন,\nমাটির ঘরে একা একা কাটবে রে আপন।\nযে চোখ দিয়ে দেখলি হারাম, সে চোখ যাবে গলে,\nআল্লাহর ভয়ে কেন রে তুই ফেললি না আঁখিজলে?",
                "এখনো সময় আছে রে ভাই, রবের কাছে ফিরে আয়,\nক্ষমা চেয়ে লুটিয়ে পড় দয়াল রবের পায়।"
            ),
            spiritualReflection = "আত্মাকে তিরস্কার করে ভালো কাজের দিকে ধাবিত করাই নফসে লাউওয়ামার ভূষণ।"
        )
    )

    val antiAddictionAdvices: List<IslamicAdvice> = listOf(
        IslamicAdvice(
            id = "gaze",
            category = "দৃষ্টি সংযম",
            titleBangla = "১. প্রথম দৃষ্টির পর চোখ ফিরিয়ে নেওয়া",
            arabicText = "النَّظْرَةُ سَهْمٌ مَسْمُومٌ مِنْ سِهَامِ إِبْلِيسَ",
            banglaTranslation = "রাসূলুল্লাহ (সা.) বলেছেন: 'কু-দৃষ্টি হলো ইবলিসের বিষাক্ত তীরগুলোর একটি।' (তাবারানী)",
            reference = "আল-মু'জামুল কাবীর",
            practicalTip = "হঠাৎ কোনো অশ্লীল ছবি বা ভিডিও সামনে এলে তৎক্ষণাৎ চোখ নামিয়ে ফোন উল্টে রাখুন এবং ৩ বার ইস্তিগফার পড়ুন।"
        ),
        IslamicAdvice(
            id = "loneliness",
            category = "একাকিত্ব পরিহার",
            titleBangla = "২. রাতে একা একা রুমে ফোন ব্যবহার না করা",
            arabicText = "لَا يُفْرِدَنَّ أَحَدُكُمْ بِنَفْسِهِ",
            banglaTranslation = "রাসূলুল্লাহ (সা.) একাকী রাত কাটানো বা একা নির্জনে থাকা অপছন্দ করতেন। (আহমাদ)",
            reference = "মুসনাদে আহমাদ: ৫৬৫০",
            practicalTip = "ঘুমানোর সময় মোবাইল রুমের বাইরে বা নাগালের বাইরে রাখুন। কার্ফিউ লক সক্রিয় রাখুন।"
        ),
        IslamicAdvice(
            id = "panic_dua",
            category = "উদ্বেগ ও প্রলোভন দমন",
            titleBangla = "৩. পাপের প্রবল ইচ্ছা জাগলে তাৎক্ষণিক দুআ",
            arabicText = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنْ شَرِّ سَمْعِي، وَمِنْ شَرِّ بَصَرِي، وَمِنْ شَرِّ لِسَانِي، وَمِنْ شَرِّ قَلْبِي، وَمِنْ شَرِّ مَنِيِّي",
            banglaTranslation = "উচ্চারণ: 'আল্লাহুম্মা ইন্নি আউজু বিকা মিন শাররি সাময়ী, ওয়া মিন শাররি বাছারী, ওয়া মিন শাররি লিসানী, ওয়া মিন শাররি ক্বলবী, ওয়া মিন শাররি মানিয়্যী।'",
            reference = "আবু দাউদ: ১৫৫১, তিরমিযী: ৩৪৯২",
            practicalTip = "অর্থ: হে আল্লাহ! আমি আপনার কাছে আশ্রয় চাই আমার কানের অনিষ্ট থেকে, চোখের অনিষ্ট থেকে, জিহ্বার অনিষ্ট থেকে, অন্তরের অনিষ্ট থেকে এবং যৌনাঙ্গের অনিষ্ট থেকে।"
        ),
        IslamicAdvice(
            id = "repentance",
            category = "তওবা ও নতুন সূচনা",
            titleBangla = "৪. ভুল হয়ে গেলে সাথে সাথে নেক কাজ করা",
            arabicText = "وَأَتْبِعِ السَّيِّئَةَ الْحَسَنَةَ تَمْحُهَا",
            banglaTranslation = "রাসূলুল্লাহ (সা.) বলেছেন: 'কোনো পাপ হয়ে গেলে তৎক্ষণাৎ একটি নেক কাজ করো, তা ওই পাপকে মুছে দেবে।' (তিরমিযী)",
            reference = "জামে তিরমিযী: ১৯৮৭",
            practicalTip = "শয়তানের ধোঁকায় পড়ে ভুল হয়ে গেলে হতাশ না হয়ে অজু করে ২ রাকাত নামাজ পড়ে কিছু সাদাকা করুন।"
        )
    )
}
