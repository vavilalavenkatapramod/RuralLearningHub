package com.example.newproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newproject.ui.theme.ClayGreen
import com.example.newproject.ui.theme.DeepSaffron
import com.example.newproject.ui.theme.NewProjectTheme
import com.example.newproject.ui.theme.RiverBlue
import java.security.MessageDigest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewProjectTheme {
                RuralLearningApp()
            }
        }
    }
}

private enum class LearningTab(val icon: ImageVector) {
    Home(Icons.Default.Home),
    Courses(Icons.Default.School),
    Resources(Icons.AutoMirrored.Filled.MenuBook),
    Notices(Icons.Default.Notifications),
    Profile(Icons.Default.Person)
}

private enum class AppLanguage(val code: String, val label: String, val nativeLabel: String) {
    English("en", "English", "English"),
    Hindi("hi", "Hindi", "हिन्दी"),
    Telugu("te", "Telugu", "తెలుగు");

    companion object {
        fun fromCode(code: String?): AppLanguage =
            entries.firstOrNull { it.code == code } ?: English
    }
}

private data class AppStrings(
    val appName: String,
    val homeTab: String,
    val classesTab: String,
    val libraryTab: String,
    val alertsTab: String,
    val profileTab: String,
    val topBarGreeting: String,
    val topBarWelcome: String,
    val loginHeroTitle: String,
    val loginHeroSubtitle: String,
    val loginHeroDetail: String,
    val languageTitle: String,
    val languageSubtitle: String,
    val loginTitle: String,
    val loginSubtitle: String,
    val fullNameLabel: String,
    val fullNameSupport: String,
    val emailLabel: String,
    val emailSupport: String,
    val phoneLabel: String,
    val phoneSupport: String,
    val roleTitle: String,
    val roleStudent: String,
    val roleTeacher: String,
    val classTitle: String,
    val secureContinue: String,
    val secureNote: String,
    val existingUserNote: String,
    val highlightCoverage: String,
    val highlightCoverageDetail: String,
    val highlightLanguage: String,
    val highlightLanguageDetail: String,
    val highlightDatabase: String,
    val highlightDatabaseDetail: String,
    val errorNameRequired: String,
    val errorContactRequired: String,
    val openLinkError: String,
    val homeHeroFirst: String,
    val homeHeroReturning: String,
    val homeHeroFirstDetail: String,
    val homeHeroReturningDetail: String,
    val useSelectedClass: String,
    val metricLevels: String,
    val metricResources: String,
    val metricStarted: String,
    val classRoomsTitle: String,
    val classRoomsSubtitle: String,
    val quickActionsTitle: String,
    val quickActionsSubtitle: String,
    val progressTitle: String,
    val progressSubtitle: String,
    val emptyProgressTitle: String,
    val emptyProgressDetail: String,
    val classesTitle: String,
    val classesAllSubtitle: String,
    val classesSelectedSubtitlePrefix: String,
    val classSearchLabel: String,
    val libraryTitle: String,
    val librarySubtitle: String,
    val filterTitle: String,
    val noticesTitle: String,
    val noticesSubtitle: String,
    val profileTitle: String,
    val profileRole: String,
    val profileClass: String,
    val profileSecurity: String,
    val profileLanguage: String,
    val profileProgressTitle: String,
    val profileEmptyProgress: String,
    val logoutLabel: String,
    val allFilter: String,
    val textbookFilter: String,
    val referenceFilter: String,
    val onlineClassFilter: String,
    val videoFilter: String,
    val supportFilter: String
)

private data class AppUser(
    val username: String,
    val email: String,
    val phone: String,
    val userId: String,
    val role: String,
    val classGroup: String,
    val isFirstLogin: Boolean
)

private data class LearningClass(
    val name: String,
    val stage: String,
    val focus: String,
    val url: String
)

private data class ResourceItem(
    val title: String,
    val type: String,
    val level: String,
    val description: String,
    val url: String
)

private data class LearningNotice(
    val title: String,
    val detail: String,
    val level: String,
    val url: String
)

private data class ProgressItem(
    val title: String,
    val status: String,
    val percent: Int
)

private data class ChoiceOption(
    val id: String,
    val label: String
)

private const val PREF_NAME = "rural_learning_session"
private const val KEY_CURRENT_USER_ID = "current_user_id"
private const val DIKSHA_URL = "https://diksha.gov.in"
private const val EPATHSHALA_URL = "https://epathshala.nic.in"
private const val SWAYAM_URL = "https://swayam.gov.in"
private const val NPTEL_URL = "https://nptel.ac.in"
private const val VIRTUAL_LABS_URL = "https://www.vlab.co.in"
private const val NDL_URL = "https://ndl.iitkgp.ac.in"
private const val SCHOLARSHIP_URL = "https://scholarships.gov.in"
private const val NCERT_YOUTUBE_URL = "https://www.youtube.com/@ncertepathshala2925"
private const val SETTINGS_LANGUAGE = "app_language"

private class LearningDatabase(context: Context) :
    SQLiteOpenHelper(context, "rural_learning_hub.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        createUsersTable(db)
        createProgressTable(db)
        createSettingsTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            createSettingsTable(db)
        }
        if (oldVersion > newVersion) {
            db.execSQL("DROP TABLE IF EXISTS settings")
            db.execSQL("DROP TABLE IF EXISTS progress")
            db.execSQL("DROP TABLE IF EXISTS users")
            onCreate(db)
        }
    }

    private fun createUsersTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS users(
                user_id TEXT PRIMARY KEY,
                username TEXT NOT NULL,
                email_hash TEXT,
                email_mask TEXT,
                phone_hash TEXT,
                phone_mask TEXT,
                role TEXT NOT NULL,
                class_group TEXT NOT NULL,
                first_login INTEGER NOT NULL,
                created_at INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    private fun createProgressTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS progress(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id TEXT NOT NULL,
                title TEXT NOT NULL,
                status TEXT NOT NULL,
                percent INTEGER NOT NULL,
                updated_at INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    private fun createSettingsTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS settings(
                setting_key TEXT PRIMARY KEY,
                setting_value TEXT NOT NULL
            )
            """.trimIndent()
        )
    }

    fun getCurrentUser(context: Context): AppUser? {
        val userId = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_CURRENT_USER_ID, null)
            ?: return null
        return getUser(userId)
    }

    fun getSelectedLanguage(): AppLanguage {
        readableDatabase.query(
            "settings",
            arrayOf("setting_value"),
            "setting_key = ?",
            arrayOf(SETTINGS_LANGUAGE),
            null,
            null,
            null
        ).use { cursor ->
            if (!cursor.moveToFirst()) return AppLanguage.English
            return AppLanguage.fromCode(cursor.getString(0))
        }
    }

    fun setSelectedLanguage(language: AppLanguage) {
        val values = ContentValues().apply {
            put("setting_key", SETTINGS_LANGUAGE)
            put("setting_value", language.code)
        }
        writableDatabase.insertWithOnConflict("settings", null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun getUser(userId: String): AppUser? {
        readableDatabase.query(
            "users",
            null,
            "user_id = ?",
            arrayOf(userId),
            null,
            null,
            null
        ).use { cursor ->
            if (!cursor.moveToFirst()) return null
            return AppUser(
                username = cursor.getString(cursor.getColumnIndexOrThrow("username")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email_mask")).orEmpty(),
                phone = cursor.getString(cursor.getColumnIndexOrThrow("phone_mask")).orEmpty(),
                userId = cursor.getString(cursor.getColumnIndexOrThrow("user_id")),
                role = cursor.getString(cursor.getColumnIndexOrThrow("role")),
                classGroup = cursor.getString(cursor.getColumnIndexOrThrow("class_group")),
                isFirstLogin = cursor.getInt(cursor.getColumnIndexOrThrow("first_login")) == 1
            )
        }
    }

    fun findUserByLogin(username: String, email: String, phone: String): AppUser? {
        val emailHash = email.trim().lowercase().takeIf { it.isNotBlank() }?.sha256()
        val phoneHash = phone.filter { it.isDigit() }.takeIf { it.isNotBlank() }?.sha256()
        val selection = when {
            emailHash != null && phoneHash != null -> "lower(username) = ? AND (email_hash = ? OR phone_hash = ?)"
            emailHash != null -> "lower(username) = ? AND email_hash = ?"
            phoneHash != null -> "lower(username) = ? AND phone_hash = ?"
            else -> "lower(username) = ?"
        }
        val args = when {
            emailHash != null && phoneHash != null -> arrayOf(username.lowercase(), emailHash, phoneHash)
            emailHash != null -> arrayOf(username.lowercase(), emailHash)
            phoneHash != null -> arrayOf(username.lowercase(), phoneHash)
            else -> arrayOf(username.lowercase())
        }
        readableDatabase.query("users", arrayOf("user_id"), selection, args, null, null, null).use { cursor ->
            if (!cursor.moveToFirst()) return null
            return getUser(cursor.getString(0))
        }
    }

    fun saveUser(context: Context, user: AppUser, rawEmail: String, rawPhone: String): AppUser {
        val values = ContentValues().apply {
            put("user_id", user.userId)
            put("username", user.username)
            put("email_hash", rawEmail.trim().lowercase().takeIf { it.isNotBlank() }?.sha256())
            put("email_mask", rawEmail.maskEmail())
            put("phone_hash", rawPhone.filter { it.isDigit() }.takeIf { it.isNotBlank() }?.sha256())
            put("phone_mask", rawPhone.maskPhone())
            put("role", user.role)
            put("class_group", user.classGroup)
            put("first_login", if (user.isFirstLogin) 1 else 0)
            put("created_at", System.currentTimeMillis())
        }
        writableDatabase.insertWithOnConflict("users", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_CURRENT_USER_ID, user.userId)
            .apply()
        return getUser(user.userId) ?: user
    }

    fun updateClass(user: AppUser, classGroup: String): AppUser {
        val values = ContentValues().apply {
            put("class_group", classGroup)
            put("first_login", 0)
        }
        writableDatabase.update("users", values, "user_id = ?", arrayOf(user.userId))
        return getUser(user.userId) ?: user.copy(classGroup = classGroup, isFirstLogin = false)
    }

    fun completeFirstLogin(user: AppUser): AppUser {
        val values = ContentValues().apply { put("first_login", 0) }
        writableDatabase.update("users", values, "user_id = ?", arrayOf(user.userId))
        return getUser(user.userId) ?: user.copy(isFirstLogin = false)
    }

    fun clearSession(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply()
    }

    fun progressFor(userId: String): List<ProgressItem> {
        readableDatabase.query(
            "progress",
            arrayOf("title", "status", "percent"),
            "user_id = ?",
            arrayOf(userId),
            null,
            null,
            "updated_at DESC"
        ).use { cursor ->
            val items = mutableListOf<ProgressItem>()
            while (cursor.moveToNext()) {
                items += ProgressItem(cursor.getString(0), cursor.getString(1), cursor.getInt(2))
            }
            return items
        }
    }

    fun markStarted(userId: String, title: String) {
        val values = ContentValues().apply {
            put("user_id", userId)
            put("title", title)
            put("status", "Started")
            put("percent", 10)
            put("updated_at", System.currentTimeMillis())
        }
        writableDatabase.insert("progress", null, values)
    }
}

@Composable
fun RuralLearningApp() {
    val context = LocalContext.current
    val database = remember { LearningDatabase(context.applicationContext) }
    var currentUser by remember { mutableStateOf(database.getCurrentUser(context)) }
    var selectedTab by remember { mutableStateOf(LearningTab.Home) }
    var selectedLanguage by remember { mutableStateOf(database.getSelectedLanguage()) }
    val strings = remember(selectedLanguage) { appStrings(selectedLanguage) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current

    val changeLanguage: (AppLanguage) -> Unit = { language ->
        selectedLanguage = language
        database.setSelectedLanguage(language)
    }

    val openLink: (String) -> Unit = { url ->
        runCatching { uriHandler.openUri(url) }
            .onFailure {
                scope.launch { snackbarHostState.showSnackbar(strings.openLinkError) }
            }
    }

    if (currentUser == null) {
        LoginScreen(
            database = database,
            strings = strings,
            selectedLanguage = selectedLanguage,
            onLanguageSelected = changeLanguage,
            onLogin = { user -> currentUser = user }
        )
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopBar(
                user = currentUser!!,
                strings = strings,
                selectedLanguage = selectedLanguage,
                onLanguageSelected = changeLanguage,
                onSearch = { openLink(DIKSHA_URL) }
            )
        },
        bottomBar = {
            NavigationBar {
                LearningTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Icon(tab.icon, contentDescription = tab.label(strings)) },
                        label = { Text(tab.label(strings), maxLines = 1, overflow = TextOverflow.Ellipsis) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Crossfade(
            targetState = selectedTab,
            label = "tab-content"
        ) { tab ->
            when (tab) {
                LearningTab.Home -> HomeScreen(
                    innerPadding = innerPadding,
                    user = currentUser!!,
                    strings = strings,
                    progress = database.progressFor(currentUser!!.userId),
                    onFinishFirstLogin = { currentUser = database.completeFirstLogin(currentUser!!) },
                    onAction = openLink,
                    onNavigate = { selectedTab = it }
                )

                LearningTab.Courses -> CoursesScreen(
                    innerPadding = innerPadding,
                    user = currentUser!!,
                    strings = strings,
                    onStart = { course ->
                        database.markStarted(currentUser!!.userId, course.name)
                        scope.launch { snackbarHostState.showSnackbar("${course.name} added to your progress") }
                        openLink(course.url)
                    }
                )

                LearningTab.Resources -> ResourcesScreen(
                    innerPadding = innerPadding,
                    user = currentUser!!,
                    strings = strings,
                    onAction = openLink
                )

                LearningTab.Notices -> NoticesScreen(
                    innerPadding = innerPadding,
                    user = currentUser!!,
                    strings = strings,
                    onAction = openLink
                )

                LearningTab.Profile -> ProfileScreen(
                    innerPadding = innerPadding,
                    user = currentUser!!,
                    strings = strings,
                    selectedLanguage = selectedLanguage,
                    progress = database.progressFor(currentUser!!.userId),
                    onClassSelected = { selectedClass -> currentUser = database.updateClass(currentUser!!, selectedClass) },
                    onLanguageSelected = changeLanguage,
                    onLogout = {
                        database.clearSession(context)
                        currentUser = null
                        selectedTab = LearningTab.Home
                    }
                )
            }
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    user: AppUser,
    strings: AppStrings,
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onSearch: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(strings.appName, fontWeight = FontWeight.Bold)
                Text(
                    if (user.isFirstLogin) "${strings.topBarWelcome} ${user.username}"
                    else "${strings.topBarGreeting} ${user.classGroup}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        actions = {
            CompactLanguageSelector(
                selected = selectedLanguage,
                onSelected = onLanguageSelected
            )
            IconButton(onClick = onSearch) {
                Icon(Icons.Default.Search, contentDescription = "Search learning content")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}

@Composable
private fun LoginScreen(
    database: LearningDatabase,
    strings: AppStrings,
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onLogin: (AppUser) -> Unit
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Student") }
    var classGroup by remember { mutableStateOf("All classes") }
    var error by remember { mutableStateOf("") }
    val canContinue = username.isNotBlank() && (email.isNotBlank() || phone.isNotBlank())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            LoginHero(strings = strings)
        }

        item {
            Card(
                modifier = Modifier.animateContentSize(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    SectionTitle(strings.languageTitle, strings.languageSubtitle)
                    LanguageSelector(
                        selected = selectedLanguage,
                        onSelected = onLanguageSelected
                    )

                    Spacer(Modifier.height(2.dp))
                    SectionTitle(strings.loginTitle, strings.loginSubtitle)
                    SimpleStatusStrip(
                        items = listOf(
                            "1" to strings.languageTitle,
                            "2" to strings.roleTitle,
                            "3" to strings.secureContinue
                        )
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            error = ""
                        },
                        label = { Text(strings.fullNameLabel) },
                        supportingText = { Text(strings.fullNameSupport) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            error = ""
                        },
                        label = { Text(strings.emailLabel) },
                        supportingText = { Text(strings.emailSupport) },
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                            error = ""
                        },
                        label = { Text(strings.phoneLabel) },
                        supportingText = { Text(strings.phoneSupport) },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OptionChipGroup(
                        title = strings.roleTitle,
                        options = listOf(
                            ChoiceOption("Student", strings.roleStudent),
                            ChoiceOption("Teacher/Admin", strings.roleTeacher)
                        ),
                        selected = role,
                        onSelected = { role = it }
                    )

                    OptionChipGroup(
                        title = strings.classTitle,
                        options = educationLevels().map { ChoiceOption(it, it) },
                        selected = classGroup,
                        onSelected = { classGroup = it }
                    )

                    AnimatedVisibility(
                        visible = error.isNotBlank(),
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
                    ) {
                        Text(error, color = MaterialTheme.colorScheme.error)
                    }

                    Button(
                        onClick = {
                            val cleanName = username.trim()
                            if (cleanName.isBlank()) {
                                error = strings.errorNameRequired
                                return@Button
                            }
                            if (email.isBlank() && phone.isBlank()) {
                                error = strings.errorContactRequired
                                return@Button
                            }
                            val existingUser = database.findUserByLogin(cleanName, email, phone)
                            if (existingUser != null) {
                                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                                    .edit()
                                    .putString(KEY_CURRENT_USER_ID, existingUser.userId)
                                    .apply()
                                onLogin(existingUser)
                            } else {
                                val newUser = AppUser(
                                    username = cleanName,
                                    email = email.maskEmail(),
                                    phone = phone.maskPhone(),
                                    userId = createUserId(cleanName, email, phone),
                                    role = role,
                                    classGroup = classGroup,
                                    isFirstLogin = true
                                )
                                onLogin(database.saveUser(context, newUser, email, phone))
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = canContinue,
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(strings.secureContinue, fontWeight = FontWeight.Bold)
                    }

                    Text(
                        strings.secureNote,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        strings.existingUserNote,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
            ) {
                LoginHighlights(strings = strings)
            }
        }
    }
}

@Composable
private fun LoginHero(strings: AppStrings) {
    val gradient = Brush.verticalGradient(
        colors = listOf(ClayGreen, RiverBlue, DeepSaffron)
    )
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(24.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(
                    color = Color.White.copy(alpha = 0.18f),
                    shape = CircleShape
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.School, contentDescription = null, tint = Color.White)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            strings.appName,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    strings.loginHeroTitle,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    strings.loginHeroSubtitle,
                    color = Color.White.copy(alpha = 0.94f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    strings.loginHeroDetail,
                    color = Color.White.copy(alpha = 0.84f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun LoginHighlights(strings: AppStrings) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MiniFeatureCard(strings.highlightCoverage, strings.highlightCoverageDetail)
        MiniFeatureCard(strings.highlightLanguage, strings.highlightLanguageDetail)
        MiniFeatureCard(strings.highlightDatabase, strings.highlightDatabaseDetail)
    }
}

@Composable
private fun HomeScreen(
    innerPadding: PaddingValues,
    user: AppUser,
    strings: AppStrings,
    progress: List<ProgressItem>,
    onFinishFirstLogin: () -> Unit,
    onAction: (String) -> Unit,
    onNavigate: (LearningTab) -> Unit
) {
    val visibleClasses = visibleClassesFor(user)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            GradientPanel(
                shape = RoundedCornerShape(28.dp),
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.tertiary,
                    DeepSaffron
                )
            ) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        if (user.isFirstLogin) strings.homeHeroFirst else strings.homeHeroReturning,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold
                        ,
                        color = Color.White
                    )
                    Text(
                        if (user.isFirstLogin) strings.homeHeroFirstDetail else strings.homeHeroReturningDetail,
                        color = Color.White.copy(alpha = 0.88f)
                    )
                    if (user.isFirstLogin) {
                        FilledTonalButton(onClick = onFinishFirstLogin, shape = RoundedCornerShape(16.dp)) {
                            Text(strings.useSelectedClass)
                        }
                    }
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                MetricCard("15", strings.metricLevels, Modifier.weight(1f))
                MetricCard("5", strings.metricResources, Modifier.weight(1f))
                MetricCard(progress.size.toString(), strings.metricStarted, Modifier.weight(1f))
            }
        }

        item {
            SectionTitle("Quick start", "Tap one large action to continue learning faster.")
        }

        item {
            QuickStartGrid(
                onCourses = { onNavigate(LearningTab.Courses) },
                onResources = { onNavigate(LearningTab.Resources) },
                onAlerts = { onNavigate(LearningTab.Notices) }
            )
        }

        item {
            SectionTitle(strings.classRoomsTitle, strings.classRoomsSubtitle)
        }

        items(visibleClasses.take(8)) { learningClass ->
            ClassCard(learningClass, onClick = { onAction(learningClass.url) })
        }

        item {
            SectionTitle(strings.quickActionsTitle, strings.quickActionsSubtitle)
        }

        item {
            QuickActions(onAction = onAction)
        }

        item {
            SectionTitle(strings.progressTitle, strings.progressSubtitle)
        }

        if (progress.isEmpty()) {
            item {
                EmptyState(
                    title = strings.emptyProgressTitle,
                    detail = strings.emptyProgressDetail
                )
            }
        } else {
            items(progress) { item -> ProgressCard(item) }
        }
    }
}

@Composable
private fun CoursesScreen(
    innerPadding: PaddingValues,
    user: AppUser,
    strings: AppStrings,
    onStart: (LearningClass) -> Unit
) {
    var search by remember { mutableStateOf("") }
    val classes = visibleClassesFor(user)
    val filtered = if (search.isBlank()) {
        classes
    } else {
        classes.filter {
            it.name.contains(search, ignoreCase = true) ||
                it.stage.contains(search, ignoreCase = true) ||
                it.focus.contains(search, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SectionTitle(
                strings.classesTitle,
                if (user.isFirstLogin) strings.classesAllSubtitle else "${strings.classesSelectedSubtitlePrefix} ${user.classGroup}"
            )
        }
        item {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text(strings.classSearchLabel) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        if (filtered.isEmpty()) {
            item {
                EmptyState(
                    title = "No classes found",
                    detail = "Try a different search word or clear the filter."
                )
            }
        } else {
            items(filtered) { learningClass ->
                ClassCard(learningClass, onClick = { onStart(learningClass) })
            }
        }
    }
}

@Composable
private fun ResourcesScreen(
    innerPadding: PaddingValues,
    user: AppUser,
    strings: AppStrings,
    onAction: (String) -> Unit
) {
    var type by remember { mutableStateOf("All") }
    val resources = resourcesFor(user).filter { type == "All" || it.type == type }
    val filters = listOf(
        ChoiceOption("All", strings.allFilter),
        ChoiceOption("Textbook", strings.textbookFilter),
        ChoiceOption("Reference", strings.referenceFilter),
        ChoiceOption("Online class", strings.onlineClassFilter),
        ChoiceOption("Video", strings.videoFilter),
        ChoiceOption("Support", strings.supportFilter)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SectionTitle(strings.libraryTitle, strings.librarySubtitle)
        }
        item {
            OptionChipGroup(
                title = strings.filterTitle,
                options = filters,
                selected = type,
                onSelected = { selected -> type = selected }
            )
        }
        if (resources.isEmpty()) {
            item {
                EmptyState(
                    title = "No resources for this filter",
                    detail = "Choose another type to explore more materials."
                )
            }
        } else {
            items(resources) { resource ->
                ResourceCard(resource, onClick = { onAction(resource.url) })
            }
        }
    }
}

@Composable
private fun NoticesScreen(
    innerPadding: PaddingValues,
    user: AppUser,
    strings: AppStrings,
    onAction: (String) -> Unit
) {
    val notices = noticesFor(user)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SectionTitle(strings.noticesTitle, strings.noticesSubtitle)
        }
        items(notices) { notice ->
            NoticeCard(notice, onClick = { onAction(notice.url) })
        }
    }
}

@Composable
private fun ProfileScreen(
    innerPadding: PaddingValues,
    user: AppUser,
    strings: AppStrings,
    selectedLanguage: AppLanguage,
    progress: List<ProgressItem>,
    onClassSelected: (String) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(strings.profileTitle, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                ProfileRow(strings.fullNameLabel, user.username)
                ProfileRow("User ID", user.userId)
                ProfileRow(strings.profileRole, displayRole(user.role, strings))
                ProfileRow(strings.profileClass, user.classGroup)
                if (user.email.isNotBlank()) ProfileRow(strings.emailLabel, user.email)
                if (user.phone.isNotBlank()) ProfileRow(strings.phoneLabel, user.phone)
                ProfileRow(strings.profileSecurity, strings.secureNote)
            }
        }

        OptionChipGroup(
            title = strings.classTitle,
            options = educationLevels().drop(1).map { ChoiceOption(it, it) },
            selected = user.classGroup,
            onSelected = onClassSelected
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(strings.profileLanguage, fontWeight = FontWeight.Bold)
            LanguageSelector(selected = selectedLanguage, onSelected = onLanguageSelected)
        }

        Card(shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(strings.profileProgressTitle, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                if (progress.isEmpty()) {
                    Text(strings.profileEmptyProgress, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    progress.forEach { ProgressCard(it) }
                }
            }
        }

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(strings.logoutLabel, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun CompactLanguageSelector(
    selected: AppLanguage,
    onSelected: (AppLanguage) -> Unit
) {
    AssistChip(
        onClick = {
            val next = when (selected) {
                AppLanguage.English -> AppLanguage.Hindi
                AppLanguage.Hindi -> AppLanguage.Telugu
                AppLanguage.Telugu -> AppLanguage.English
            }
            onSelected(next)
        },
        label = { Text(selected.nativeLabel) },
        leadingIcon = {
            Icon(
                Icons.Default.Language,
                contentDescription = null,
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )
}

@Composable
private fun LanguageSelector(
    selected: AppLanguage,
    onSelected: (AppLanguage) -> Unit
) {
    OptionChipGroup(
        title = "",
        options = AppLanguage.entries.map { ChoiceOption(it.code, "${it.label} - ${it.nativeLabel}") },
        selected = selected.code,
        onSelected = { code -> onSelected(AppLanguage.fromCode(code)) },
        showTitle = false
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun QuickActions(onAction: (String) -> Unit) {
    val actions = listOf(
        Triple("Textbooks", EPATHSHALA_URL, Icons.Default.Book),
        Triple("References", NDL_URL, Icons.AutoMirrored.Filled.MenuBook),
        Triple("Online classes", SWAYAM_URL, Icons.Default.School),
        Triple("Labs", VIRTUAL_LABS_URL, Icons.Default.Engineering),
        Triple("YouTube", NCERT_YOUTUBE_URL, Icons.Default.VideoLibrary),
        Triple("Scholarships", SCHOLARSHIP_URL, Icons.AutoMirrored.Filled.Assignment)
    )
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        actions.forEach { (label, url, icon) ->
            AssistChip(
                onClick = { onAction(url) },
                label = { Text(label) },
                leadingIcon = { Icon(icon, contentDescription = null, modifier = Modifier.size(AssistChipDefaults.IconSize)) }
            )
        }
    }
}

@Composable
private fun QuickStartGrid(
    onCourses: () -> Unit,
    onResources: () -> Unit,
    onAlerts: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            ShortcutCard(
                title = "Classes",
                subtitle = "Continue where you want to learn",
                icon = Icons.Default.School,
                accent = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
                onClick = onCourses
            )
            ShortcutCard(
                title = "Library",
                subtitle = "Books, labs and references",
                icon = Icons.AutoMirrored.Filled.MenuBook,
                accent = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f),
                onClick = onResources
            )
        }
        ShortcutCard(
            title = "Alerts",
            subtitle = "Scholarships, reminders and updates",
            icon = Icons.Default.Notifications,
            accent = DeepSaffron,
            modifier = Modifier.fillMaxWidth(),
            onClick = onAlerts
        )
    }
}

@Composable
private fun ClassCard(learningClass: LearningClass, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .animateContentSize(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(10.dp)
            ) {
                Icon(Icons.Default.School, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(learningClass.name, fontWeight = FontWeight.Bold)
                Text(learningClass.stage, color = MaterialTheme.colorScheme.primary)
                Text(learningClass.focus, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(
                "Open",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ResourceCard(resource: ResourceItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .animateContentSize(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(iconForResource(resource.type), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(resource.title, fontWeight = FontWeight.Bold)
                    Text("${resource.type} - ${resource.level}", color = MaterialTheme.colorScheme.primary)
                }
            }
            Text(resource.description, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun NoticeCard(notice: LearningNotice, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .animateContentSize(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Notifications, contentDescription = null)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(notice.title, fontWeight = FontWeight.Bold)
                Text(notice.level, color = MaterialTheme.colorScheme.primary)
                Text(notice.detail, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun ProgressCard(item: ProgressItem) {
    Card(
        modifier = Modifier.animateContentSize(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(item.title, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                Text("${item.percent}%")
            }
            LinearProgressIndicator(
                progress = { item.percent / 100f },
                modifier = Modifier.fillMaxWidth()
            )
            Text(item.status, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun EmptyState(title: String, detail: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(detail, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun MetricCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.animateContentSize(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
            Text(
                label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MiniFeatureCard(title: String, detail: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().animateContentSize(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun GradientPanel(
    shape: RoundedCornerShape,
    colors: List<Color>,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(colors))
                .padding(1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(Color.Transparent),
                content = content
            )
        }
    }
}

@Composable
private fun ShortcutCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick).animateContentSize(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.16f))
                    .padding(10.dp)
            ) {
                Icon(icon, contentDescription = null, tint = accent)
            }
            Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SimpleStatusStrip(items: List<Pair<String, String>>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { (step, label) ->
            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(step, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                    Text(label, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OptionChipGroup(
    title: String,
    options: List<ChoiceOption>,
    selected: String,
    onSelected: (String) -> Unit,
    showTitle: Boolean = true
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (showTitle && title.isNotBlank()) {
            Text(title, fontWeight = FontWeight.Bold)
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { option ->
                AssistChip(
                    onClick = { onSelected(option.id) },
                    label = { Text(option.label) },
                    leadingIcon = {
                        if (selected == option.id) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (selected == option.id) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                )
            }
        }
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 12.dp))
    }
}

private fun LearningTab.label(strings: AppStrings): String = when (this) {
    LearningTab.Home -> strings.homeTab
    LearningTab.Courses -> strings.classesTab
    LearningTab.Resources -> strings.libraryTab
    LearningTab.Notices -> strings.alertsTab
    LearningTab.Profile -> strings.profileTab
}

private fun displayRole(role: String, strings: AppStrings): String = when (role) {
    "Teacher/Admin" -> strings.roleTeacher
    else -> strings.roleStudent
}

private fun educationLevels() = listOf(
    "All classes",
    "Class 5",
    "Class 6",
    "Class 7",
    "Class 8",
    "Class 9",
    "Class 10",
    "Inter 1st Year",
    "Inter 2nd Year",
    "BTech Year 1",
    "BTech Year 2",
    "BTech Year 3",
    "BTech Year 4",
    "MTech Year 1",
    "MTech Year 2"
)

private fun visibleClassesFor(user: AppUser): List<LearningClass> {
    val classes = allLearningClasses()
    if (user.isFirstLogin || user.classGroup == "All classes") return classes
    return classes.filter { it.name == user.classGroup }.ifEmpty { classes }
}

private fun allLearningClasses() = listOf(
    LearningClass("Class 5", "School", "Math basics, EVS, English reading, Telugu/Hindi support", DIKSHA_URL),
    LearningClass("Class 6", "School", "Science foundations, social studies, math practice", DIKSHA_URL),
    LearningClass("Class 7", "School", "NCERT textbooks, experiments and revision worksheets", EPATHSHALA_URL),
    LearningClass("Class 8", "School", "Bridge learning for high school, coding basics and science labs", EPATHSHALA_URL),
    LearningClass("Class 9", "School", "Board foundation, math, physics, chemistry and biology", DIKSHA_URL),
    LearningClass("Class 10", "School", "Board exam preparation, model papers and official videos", DIKSHA_URL),
    LearningClass("Inter 1st Year", "Intermediate", "MPC, BiPC, commerce, civics and language resources", SWAYAM_URL),
    LearningClass("Inter 2nd Year", "Intermediate", "Entrance revision, practicals, model papers and career guidance", SWAYAM_URL),
    LearningClass("BTech Year 1", "Undergraduate", "Engineering math, physics, programming and communication", NPTEL_URL),
    LearningClass("BTech Year 2", "Undergraduate", "Core branch foundations, labs and reference books", NPTEL_URL),
    LearningClass("BTech Year 3", "Undergraduate", "Advanced subjects, projects, internships and GATE basics", NPTEL_URL),
    LearningClass("BTech Year 4", "Undergraduate", "Placements, final project, GATE, higher studies and interviews", NPTEL_URL),
    LearningClass("MTech Year 1", "Postgraduate", "Research methods, advanced electives and seminar planning", NPTEL_URL),
    LearningClass("MTech Year 2", "Postgraduate", "Thesis, publications, lab work and career preparation", NDL_URL)
)

private fun resourcesFor(user: AppUser): List<ResourceItem> {
    val level = if (user.isFirstLogin) "All levels" else user.classGroup
    return listOf(
        ResourceItem("NCERT ePathshala textbooks", "Textbook", level, "Official school textbooks and learning materials.", EPATHSHALA_URL),
        ResourceItem("DIKSHA practice and teacher content", "Online class", level, "Class-wise lessons, quizzes and downloadable content.", DIKSHA_URL),
        ResourceItem("SWAYAM online courses", "Online class", level, "Free online courses for intermediate, degree and skill learning.", SWAYAM_URL),
        ResourceItem("NPTEL engineering courses", "Online class", level, "IIT/IISc lectures for BTech and MTech students.", NPTEL_URL),
        ResourceItem("National Digital Library", "Reference", level, "Books, papers and reference material for school to research level.", NDL_URL),
        ResourceItem("Virtual Labs", "Reference", level, "Remote lab simulations for science and engineering practice.", VIRTUAL_LABS_URL),
        ResourceItem("NCERT official video lessons", "Video", level, "YouTube is available as one option, not the only option.", NCERT_YOUTUBE_URL),
        ResourceItem("National Scholarship Portal", "Support", level, "Scholarship discovery and application support.", SCHOLARSHIP_URL)
    )
}

private fun noticesFor(user: AppUser): List<LearningNotice> {
    val level = if (user.isFirstLogin) "All classes" else user.classGroup
    return listOf(
        LearningNotice("Textbook update", "Open official books and notes for $level.", level, EPATHSHALA_URL),
        LearningNotice("Online class reminder", "Check free SWAYAM/DIKSHA classes for today.", level, SWAYAM_URL),
        LearningNotice("Reference practice", "Use National Digital Library and Virtual Labs for deeper learning.", level, NDL_URL),
        LearningNotice("Scholarship check", "Review scholarship opportunities before deadlines.", level, SCHOLARSHIP_URL)
    )
}

private fun iconForResource(type: String): ImageVector = when (type) {
    "Textbook" -> Icons.Default.Book
    "Reference" -> Icons.AutoMirrored.Filled.MenuBook
    "Online class" -> Icons.Default.School
    "Video" -> Icons.Default.VideoLibrary
    "Support" -> Icons.AutoMirrored.Filled.Chat
    else -> Icons.Default.Star
}

private fun createUserId(username: String, email: String, phone: String): String {
    val seed = "$username|$email|$phone|${System.currentTimeMillis()}".sha256()
    val nameCode = username.filter { it.isLetterOrDigit() }.take(4).uppercase().ifBlank { "USER" }
    return "RLH-$nameCode-${seed.take(6).uppercase()}"
}

private fun String.sha256(): String {
    val digest = MessageDigest.getInstance("SHA-256").digest(toByteArray())
    return digest.joinToString("") { "%02x".format(it) }
}

private fun String.maskEmail(): String {
    val clean = trim()
    if (clean.isBlank() || !clean.contains("@")) return ""
    val name = clean.substringBefore("@")
    val domain = clean.substringAfter("@")
    val visible = name.take(2).ifBlank { "*" }
    return "$visible***@$domain"
}

private fun String.maskPhone(): String {
    val digits = filter { it.isDigit() }
    if (digits.isBlank()) return ""
    return "******${digits.takeLast(4)}"
}

private fun appStrings(language: AppLanguage): AppStrings = when (language) {
    AppLanguage.English -> AppStrings(
        appName = "Rural Learning Hub",
        homeTab = "Home",
        classesTab = "Classes",
        libraryTab = "Library",
        alertsTab = "Alerts",
        profileTab = "Profile",
        topBarGreeting = "Focused on",
        topBarWelcome = "Welcome back,",
        loginHeroTitle = "A smarter entry point for every learner",
        loginHeroSubtitle = "Sign in once, choose your class, and keep books, classes, alerts and scholarships in one place.",
        loginHeroDetail = "The app now remembers language preference and stores student profiles in a local SQLite database.",
        languageTitle = "Language",
        languageSubtitle = "Pick the app language before you continue.",
        loginTitle = "Secure login or create profile",
        loginSubtitle = "Use your name plus email or phone. Returning users are matched locally and new users get a fresh profile.",
        fullNameLabel = "Student name",
        fullNameSupport = "Use the same name next time for quick login.",
        emailLabel = "Email",
        emailSupport = "Stored as a secure hash with a masked preview.",
        phoneLabel = "Phone",
        phoneSupport = "Only the last 4 digits are shown in the profile.",
        roleTitle = "Role",
        roleStudent = "Student",
        roleTeacher = "Teacher / Admin",
        classTitle = "Class or year",
        secureContinue = "Continue securely",
        secureNote = "Contact details are hashed before they are stored in the local database.",
        existingUserNote = "If the profile already exists, the app signs the same learner back in automatically.",
        highlightCoverage = "15 learning levels",
        highlightCoverageDetail = "From Class 5 to MTech in one app.",
        highlightLanguage = "3 language modes",
        highlightLanguageDetail = "English, Hindi and Telugu ready.",
        highlightDatabase = "Local database",
        highlightDatabaseDetail = "Users, progress and language settings are saved offline.",
        errorNameRequired = "Enter student name",
        errorContactRequired = "Enter email or phone",
        openLinkError = "Could not open link. Check browser or internet.",
        homeHeroFirst = "Everything is open for your first visit",
        homeHeroReturning = "Your learning room is ready",
        homeHeroFirstDetail = "Explore every class now. From your next visit, the app will focus on your selected class or year.",
        homeHeroReturningDetail = "Books, online classes, references and alerts are tuned to your profile.",
        useSelectedClass = "Use my selected class next time",
        metricLevels = "Levels",
        metricResources = "Resource types",
        metricStarted = "Started",
        classRoomsTitle = "Class rooms",
        classRoomsSubtitle = "School, intermediate, BTech and MTech are all available.",
        quickActionsTitle = "Quick learning options",
        quickActionsSubtitle = "Open official books, references, labs, video lessons and scholarships fast.",
        progressTitle = "Your progress",
        progressSubtitle = "Only the work you start is tracked for your profile.",
        emptyProgressTitle = "No activity yet",
        emptyProgressDetail = "Open a class from the Classes tab to begin your own learning trail.",
        classesTitle = "Classes",
        classesAllSubtitle = "Showing all levels for your first login",
        classesSelectedSubtitlePrefix = "Showing",
        classSearchLabel = "Search class, year or subject",
        libraryTitle = "Library",
        librarySubtitle = "Use more than YouTube with official textbooks, libraries, live classes and labs.",
        filterTitle = "Filter",
        noticesTitle = "Notifications",
        noticesSubtitle = "Class reminders, textbook updates, scholarships and online sessions.",
        profileTitle = "Learner profile",
        profileRole = "Role",
        profileClass = "Class / Year",
        profileSecurity = "Security",
        profileLanguage = "Preferred language",
        profileProgressTitle = "My progress",
        profileEmptyProgress = "Fresh account. No completed work yet.",
        logoutLabel = "Logout / switch learner",
        allFilter = "All",
        textbookFilter = "Textbook",
        referenceFilter = "Reference",
        onlineClassFilter = "Online class",
        videoFilter = "Video",
        supportFilter = "Support"
    )

    AppLanguage.Hindi -> AppStrings(
        appName = "रूरल लर्निंग हब",
        homeTab = "होम",
        classesTab = "कक्षाएं",
        libraryTab = "लाइब्रेरी",
        alertsTab = "अलर्ट",
        profileTab = "प्रोफाइल",
        topBarGreeting = "फोकस",
        topBarWelcome = "फिर से स्वागत है,",
        loginHeroTitle = "हर विद्यार्थी के लिए बेहतर शुरुआत",
        loginHeroSubtitle = "एक बार लॉगिन करें, अपनी कक्षा चुनें, और किताबें, क्लास, अलर्ट और छात्रवृत्ति एक ही जगह पाएं।",
        loginHeroDetail = "ऐप अब भाषा पसंद याद रखता है और छात्र प्रोफाइल लोकल SQLite डेटाबेस में सेव करता है।",
        languageTitle = "भाषा",
        languageSubtitle = "आगे बढ़ने से पहले ऐप की भाषा चुनें।",
        loginTitle = "सुरक्षित लॉगिन या नया प्रोफाइल",
        loginSubtitle = "नाम के साथ ईमेल या फोन दें। पुराने उपयोगकर्ता लोकली मिलते हैं और नए उपयोगकर्ताओं के लिए नया प्रोफाइल बनता है।",
        fullNameLabel = "विद्यार्थी का नाम",
        fullNameSupport = "अगली बार जल्दी लॉगिन के लिए यही नाम रखें।",
        emailLabel = "ईमेल",
        emailSupport = "सुरक्षित हैश के रूप में सेव होगा और केवल मास्क रूप दिखेगा।",
        phoneLabel = "फोन",
        phoneSupport = "प्रोफाइल में केवल अंतिम 4 अंक दिखेंगे।",
        roleTitle = "भूमिका",
        roleStudent = "विद्यार्थी",
        roleTeacher = "शिक्षक / एडमिन",
        classTitle = "कक्षा या वर्ष",
        secureContinue = "सुरक्षित रूप से आगे बढ़ें",
        secureNote = "संपर्क विवरण लोकल डेटाबेस में सेव करने से पहले हैश किए जाते हैं।",
        existingUserNote = "यदि प्रोफाइल पहले से है, तो वही विद्यार्थी अपने आप लॉगिन हो जाएगा।",
        highlightCoverage = "15 स्तर",
        highlightCoverageDetail = "कक्षा 5 से MTech तक एक ही ऐप में।",
        highlightLanguage = "3 भाषा मोड",
        highlightLanguageDetail = "English, हिन्दी और తెలుగు उपलब्ध।",
        highlightDatabase = "लोकल डेटाबेस",
        highlightDatabaseDetail = "यूजर, प्रोग्रेस और भाषा सेटिंग ऑफलाइन सेव होती हैं।",
        errorNameRequired = "विद्यार्थी का नाम दर्ज करें",
        errorContactRequired = "ईमेल या फोन दर्ज करें",
        openLinkError = "लिंक नहीं खुला। ब्राउज़र या इंटरनेट जांचें।",
        homeHeroFirst = "पहली बार के लिए सब कुछ खुला है",
        homeHeroReturning = "आपका लर्निंग रूम तैयार है",
        homeHeroFirstDetail = "अभी सभी कक्षाएं देखें। अगली बार ऐप आपकी चुनी हुई कक्षा या वर्ष पर फोकस करेगा।",
        homeHeroReturningDetail = "किताबें, ऑनलाइन क्लास, रेफरेंस और अलर्ट आपकी प्रोफाइल के अनुसार दिख रहे हैं।",
        useSelectedClass = "अगली बार मेरी चुनी हुई कक्षा उपयोग करें",
        metricLevels = "स्तर",
        metricResources = "संसाधन प्रकार",
        metricStarted = "शुरू",
        classRoomsTitle = "कक्षा कक्ष",
        classRoomsSubtitle = "स्कूल, इंटर, BTech और MTech सभी उपलब्ध हैं।",
        quickActionsTitle = "जल्दी सीखने के विकल्प",
        quickActionsSubtitle = "आधिकारिक किताबें, रेफरेंस, लैब, वीडियो और छात्रवृत्ति जल्दी खोलें।",
        progressTitle = "आपकी प्रगति",
        progressSubtitle = "केवल वही काम ट्रैक होगा जिसे आप शुरू करेंगे।",
        emptyProgressTitle = "अभी कोई गतिविधि नहीं",
        emptyProgressDetail = "अपना सीखने का सफर शुरू करने के लिए Classes टैब से कोई कक्षा खोलें।",
        classesTitle = "कक्षाएं",
        classesAllSubtitle = "पहले लॉगिन के लिए सभी स्तर दिख रहे हैं",
        classesSelectedSubtitlePrefix = "दिख रहा है",
        classSearchLabel = "कक्षा, वर्ष या विषय खोजें",
        libraryTitle = "लाइब्रेरी",
        librarySubtitle = "सिर्फ YouTube नहीं, आधिकारिक किताबें, लाइब्रेरी, लाइव क्लास और लैब भी।",
        filterTitle = "फ़िल्टर",
        noticesTitle = "सूचनाएं",
        noticesSubtitle = "कक्षा रिमाइंडर, किताब अपडेट, छात्रवृत्ति और ऑनलाइन सेशन।",
        profileTitle = "विद्यार्थी प्रोफाइल",
        profileRole = "भूमिका",
        profileClass = "कक्षा / वर्ष",
        profileSecurity = "सुरक्षा",
        profileLanguage = "पसंदीदा भाषा",
        profileProgressTitle = "मेरी प्रगति",
        profileEmptyProgress = "नया खाता। अभी कोई पूरा काम नहीं।",
        logoutLabel = "लॉगआउट / विद्यार्थी बदलें",
        allFilter = "सभी",
        textbookFilter = "पाठ्यपुस्तक",
        referenceFilter = "रेफरेंस",
        onlineClassFilter = "ऑनलाइन क्लास",
        videoFilter = "वीडियो",
        supportFilter = "सहायता"
    )

    AppLanguage.Telugu -> AppStrings(
        appName = "రూరల్ లెర్నింగ్ హబ్",
        homeTab = "హోమ్",
        classesTab = "తరగతులు",
        libraryTab = "లైబ్రరీ",
        alertsTab = "అలర్ట్స్",
        profileTab = "ప్రొఫైల్",
        topBarGreeting = "ఫోకస్",
        topBarWelcome = "మళ్లీ స్వాగతం,",
        loginHeroTitle = "ప్రతి విద్యార్థికి మరింత మంచి ప్రారంభం",
        loginHeroSubtitle = "ఒక్కసారి లాగిన్ అవండి, మీ తరగతి ఎంచుకోండి, పుస్తకాలు, క్లాసులు, అలర్ట్స్ మరియు స్కాలర్‌షిప్‌లు ఒకేచోట పొందండి.",
        loginHeroDetail = "ఈ యాప్ ఇప్పుడు భాష ఎంపికను గుర్తుంచుకుంటుంది మరియు విద్యార్థి ప్రొఫైల్‌లను స్థానిక SQLite డేటాబేస్‌లో నిల్వ చేస్తుంది.",
        languageTitle = "భాష",
        languageSubtitle = "ముందుకు వెళ్లే ముందు యాప్ భాషను ఎంచుకోండి.",
        loginTitle = "సురక్షిత లాగిన్ లేదా కొత్త ప్రొఫైల్",
        loginSubtitle = "మీ పేరు తో పాటు ఈమెయిల్ లేదా ఫోన్ ఇవ్వండి. పాత వినియోగదారులు లోకల్‌గా కనుగొనబడతారు, కొత్త వారికి కొత్త ప్రొఫైల్ సృష్టించబడుతుంది.",
        fullNameLabel = "విద్యార్థి పేరు",
        fullNameSupport = "తర్వాత త్వరగా లాగిన్ కావడానికి ఇదే పేరు ఉపయోగించండి.",
        emailLabel = "ఈమెయిల్",
        emailSupport = "సెక్యూర్ హాష్‌గా సేవ్ అవుతుంది, మాస్క్ చేసిన రూపమే కనిపిస్తుంది.",
        phoneLabel = "ఫోన్",
        phoneSupport = "ప్రొఫైల్‌లో చివరి 4 అంకెలు మాత్రమే కనిపిస్తాయి.",
        roleTitle = "పాత్ర",
        roleStudent = "విద్యార్థి",
        roleTeacher = "గురువు / అడ్మిన్",
        classTitle = "తరగతి లేదా సంవత్సరం",
        secureContinue = "సురక్షితంగా కొనసాగించండి",
        secureNote = "సంప్రదింపు వివరాలు లోకల్ డేటాబేస్‌లో సేవ్ చేసేముందు హాష్ చేయబడతాయి.",
        existingUserNote = "ప్రొఫైల్ ఇప్పటికే ఉంటే అదే విద్యార్థి ఆటోమేటిక్‌గా లాగిన్ అవుతాడు.",
        highlightCoverage = "15 విద్యా స్థాయులు",
        highlightCoverageDetail = "5వ తరగతి నుండి MTech వరకు ఒకే యాప్‌లో.",
        highlightLanguage = "3 భాషా మోడ్‌లు",
        highlightLanguageDetail = "English, हिन्दी, తెలుగు సిద్ధంగా ఉన్నాయి.",
        highlightDatabase = "లోకల్ డేటాబేస్",
        highlightDatabaseDetail = "యూజర్లు, ప్రోగ్రెస్, భాషా సెట్టింగ్స్ ఆఫ్లైన్‌లో సేవ్ అవుతాయి.",
        errorNameRequired = "విద్యార్థి పేరు నమోదు చేయండి",
        errorContactRequired = "ఈమెయిల్ లేదా ఫోన్ నమోదు చేయండి",
        openLinkError = "లింక్ తెరవలేకపోయాం. బ్రౌజర్ లేదా ఇంటర్నెట్‌ను తనిఖీ చేయండి.",
        homeHeroFirst = "మీ మొదటి సందర్శన కోసం అన్నీ తెరిచి ఉన్నాయి",
        homeHeroReturning = "మీ లెర్నింగ్ రూమ్ సిద్ధంగా ఉంది",
        homeHeroFirstDetail = "ఇప్పుడే అన్ని తరగతులను చూడండి. తర్వాతి సందర్శనలో యాప్ మీ ఎంచుకున్న తరగతి లేదా సంవత్సరంపై ఫోకస్ చేస్తుంది.",
        homeHeroReturningDetail = "పుస్తకాలు, ఆన్‌లైన్ క్లాసులు, రిఫరెన్సులు, అలర్ట్స్ మీ ప్రొఫైల్‌కి సరిపడేలా ఉన్నాయి.",
        useSelectedClass = "తర్వాత నా ఎంచుకున్న తరగతినే ఉపయోగించండి",
        metricLevels = "స్థాయులు",
        metricResources = "వనరుల రకాలు",
        metricStarted = "ప్రారంభించినవి",
        classRoomsTitle = "తరగతి గదులు",
        classRoomsSubtitle = "స్కూల్, ఇంటర్, BTech, MTech అన్నీ అందుబాటులో ఉన్నాయి.",
        quickActionsTitle = "త్వరిత అభ్యాస ఎంపికలు",
        quickActionsSubtitle = "ఆధికారిక పుస్తకాలు, రిఫరెన్సులు, ల్యాబ్స్, వీడియో పాఠాలు, స్కాలర్‌షిప్‌లను వెంటనే తెరవండి.",
        progressTitle = "మీ పురోగతి",
        progressSubtitle = "మీరు ప్రారంభించిన పనులు మాత్రమే ట్రాక్ అవుతాయి.",
        emptyProgressTitle = "ఇంకా కార్యకలాపం లేదు",
        emptyProgressDetail = "మీ అభ్యాస ప్రయాణం ప్రారంభించడానికి Classes ట్యాబ్‌లో ఒక తరగతి తెరవండి.",
        classesTitle = "తరగతులు",
        classesAllSubtitle = "మొదటి లాగిన్ కోసం అన్ని స్థాయిలు చూపిస్తున్నాం",
        classesSelectedSubtitlePrefix = "చూపిస్తున్నది",
        classSearchLabel = "తరగతి, సంవత్సరం లేదా విషయం వెతకండి",
        libraryTitle = "లైబ్రరీ",
        librarySubtitle = "YouTube మాత్రమే కాదు, అధికారిక పుస్తకాలు, లైబ్రరీలు, లైవ్ క్లాసులు, ల్యాబ్స్ కూడా ఉన్నాయి.",
        filterTitle = "ఫిల్టర్",
        noticesTitle = "నోటిఫికేషన్స్",
        noticesSubtitle = "తరగతి గుర్తింపులు, పుస్తక అప్‌డేట్లు, స్కాలర్‌షిప్‌లు, ఆన్‌లైన్ సెషన్లు.",
        profileTitle = "విద్యార్థి ప్రొఫైల్",
        profileRole = "పాత్ర",
        profileClass = "తరగతి / సంవత్సరం",
        profileSecurity = "భద్రత",
        profileLanguage = "ఇష్టమైన భాష",
        profileProgressTitle = "నా పురోగతి",
        profileEmptyProgress = "కొత్త ఖాతా. ఇంకా పూర్తి చేసిన పని లేదు.",
        logoutLabel = "లాగౌట్ / విద్యార్థిని మార్చండి",
        allFilter = "అన్నీ",
        textbookFilter = "పాఠ్యపుస్తకం",
        referenceFilter = "రిఫరెన్స్",
        onlineClassFilter = "ఆన్‌లైన్ క్లాస్",
        videoFilter = "వీడియో",
        supportFilter = "సహాయం"
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RuralLearningAppPreview() {
    NewProjectTheme {
        Surface {
            RuralLearningApp()
        }
    }
}
