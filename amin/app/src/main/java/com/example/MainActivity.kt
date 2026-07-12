package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.CompareScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SummarizeScreen
import com.example.ui.screens.MergeScreen
import com.example.ui.screens.SplitScreen
import com.example.ui.screens.CompressScreen
import com.example.ui.screens.ConvertScreen
import com.example.ui.screens.ProtectScreen
import com.example.ui.screens.DefaultActionScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PDFlyApp()
            }
        }
    }
}

@Composable
fun PDFlyApp() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(navController = navController, startDestination = "dashboard") {
            composable("dashboard") {
                DashboardScreen(navController, innerPadding)
            }
            composable("summarize") {
                SummarizeScreen(navController, innerPadding)
            }
            composable("chat") {
                ChatScreen(navController, innerPadding)
            }
            composable("chatbot_help") {
                com.example.ui.screens.ChatbotHelpScreen(navController, innerPadding)
            }
            composable("compare") {
                CompareScreen(navController, innerPadding)
            }
            composable("settings") {
                SettingsScreen(navController, innerPadding)
            }
            composable("merge") { MergeScreen(navController, innerPadding) }
            composable("split") { SplitScreen(navController, innerPadding) }
            composable("compress") { CompressScreen(navController, innerPadding) }
            composable("convert") { ConvertScreen(navController, innerPadding, false) }
            composable("topdf") { ConvertScreen(navController, innerPadding, true) }
            composable("protect") { ProtectScreen(navController, innerPadding) }
            
            composable("pdf_to_word") { DefaultActionScreen(navController, innerPadding, "PDF to Word", Icons.Outlined.Description, R.drawable.bg_convert) }
            composable("pdf_to_excel") { DefaultActionScreen(navController, innerPadding, "PDF to Excel", Icons.Outlined.TableChart, R.drawable.bg_convert) }
            composable("word_to_pdf") { DefaultActionScreen(navController, innerPadding, "Word to PDF", Icons.Outlined.Description, R.drawable.bg_convert) }
            composable("excel_to_pdf") { DefaultActionScreen(navController, innerPadding, "Excel to PDF", Icons.Outlined.TableChart, R.drawable.bg_convert) }
            composable("pdf_to_ppt") { DefaultActionScreen(navController, innerPadding, "PDF to PPT", Icons.Outlined.Slideshow, R.drawable.bg_convert) }
            composable("ppt_to_pdf") { DefaultActionScreen(navController, innerPadding, "PPT to PDF", Icons.Outlined.Slideshow, R.drawable.bg_convert) }
            
            composable("edit") { DefaultActionScreen(navController, innerPadding, "Edit PDF", Icons.Outlined.Edit) }
            composable("expand") { DefaultActionScreen(navController, innerPadding, "Expand PDF", Icons.Outlined.OpenWith) }
            composable("extract") { DefaultActionScreen(navController, innerPadding, "Extract Tables", Icons.Outlined.TableChart) }
            composable("translate") { DefaultActionScreen(navController, innerPadding, "Translate PDF", Icons.Outlined.Translate, R.drawable.bg_ai) }
            composable("ocr") { DefaultActionScreen(navController, innerPadding, "Smart OCR", Icons.Outlined.DocumentScanner, R.drawable.bg_ai) }
            composable("eli5") { DefaultActionScreen(navController, innerPadding, "Explain Like I'm 5", Icons.Outlined.Face, R.drawable.bg_ai) }
            composable("tone_mapper") { DefaultActionScreen(navController, innerPadding, "Tone Mapper", Icons.Outlined.Mood, R.drawable.bg_ai) }
            composable("contradict") { DefaultActionScreen(navController, innerPadding, "Contradiction Spotter", Icons.Outlined.Gavel, R.drawable.bg_ai) }
            composable("podcast") { DefaultActionScreen(navController, innerPadding, "PDF to Podcast", Icons.Outlined.Mic, R.drawable.bg_ai) }
            composable("red_team") { DefaultActionScreen(navController, innerPadding, "Red Team Document", Icons.Outlined.Security, R.drawable.bg_security) }
            composable("argue_map") { com.example.ui.screens.ArgumentMapperScreen(navController, innerPadding) }
            
            composable("time_machine") { com.example.ui.screens.TimeMachineScreen(navController, innerPadding) }
            composable("hallucination") { DefaultActionScreen(navController, innerPadding, "Hallucination Detector", Icons.Outlined.Troubleshoot, R.drawable.bg_ai) }
            composable("ghostwriter") { DefaultActionScreen(navController, innerPadding, "Ghostwriter Forensics", Icons.Outlined.PersonSearch, R.drawable.bg_ai) }
            composable("memory_vault") { DefaultActionScreen(navController, innerPadding, "Memory Vault", Icons.Outlined.Storage, R.drawable.bg_ai) }
            composable("leak_scanner") { com.example.ui.screens.LeakScannerScreen(navController, innerPadding) }
            composable("whats_missing") { DefaultActionScreen(navController, innerPadding, "What's Missing? Analysis", Icons.Outlined.QuestionMark, R.drawable.bg_ai) }
            composable("sign_analyzer") { DefaultActionScreen(navController, innerPadding, "Signature Behavior Analyzer", Icons.Outlined.Draw, R.drawable.bg_ai) }
            composable("pdf_to_code") { DefaultActionScreen(navController, innerPadding, "PDF to Code", Icons.Outlined.Code, R.drawable.bg_ai) }
            composable("deep_fake") { DefaultActionScreen(navController, innerPadding, "Deep Fake Document Detector", Icons.Outlined.NoPhotography, R.drawable.bg_security) }
            composable("reading_race") { DefaultActionScreen(navController, innerPadding, "Reading Race Mode", Icons.Outlined.Speed, R.drawable.bg_ai) }
            composable("doc_battle") { DefaultActionScreen(navController, innerPadding, "Document Battle", Icons.Outlined.SportsMartialArts, R.drawable.bg_ai) }
            composable("escape_room") { DefaultActionScreen(navController, innerPadding, "PDF Escape Room", Icons.Outlined.Key, R.drawable.bg_ai) }
        }
    }
}
