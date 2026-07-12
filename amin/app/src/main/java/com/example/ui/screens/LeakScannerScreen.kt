package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class LeakItem(
    val type: String,
    val severity: String,
    val description: String,
    val occurrences: Int
)

val sampleLeaks = listOf(
    LeakItem("Social Security Number", "Critical", "Found 2 instances of potential SSN formats in text.", 2),
    LeakItem("Credit Card Data", "Critical", "Found 1 instance of 16-digit PAN pattern.", 1),
    LeakItem("Internal Project Code", "High", "Found mentions of 'Project Titan' and 'Project Apollo'.", 4),
    LeakItem("Email Addresses", "Medium", "Found 15 personal email addresses belonging to domain @external.com.", 15),
    LeakItem("Phone Numbers", "Low", "Found 3 phone numbers in formatting +1 (XXX) XXX-XXXX.", 3)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeakScannerScreen(navController: NavController, innerPadding: PaddingValues) {
    var isProcessing by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (showResult) "Scan Results" else "Leak Scanner") },
                navigationIcon = {
                    IconButton(onClick = { 
                        if (showResult) {
                            showResult = false
                            isProcessing = false
                        } else {
                            navController.popBackStack() 
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { padding ->
        AnimatedContent(
            targetState = showResult,
            transitionSpec = {
                fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
            },
            modifier = Modifier.padding(padding).fillMaxSize(),
            label = "LeakScanTransition"
        ) { isResult ->
            if (isResult) {
                LeakScanResultView()
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (isProcessing) {
                            CircularProgressIndicator(modifier = Modifier.size(64.dp), color = Color(0xFFE53935))
                            Spacer(modifier = Modifier.height(24.dp))
                            Text("Scanning document for confidential data...", style = MaterialTheme.typography.titleMedium)
                        } else {
                            Icon(
                                Icons.Outlined.PrivacyTip,
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                tint = Color(0xFFE53935)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                "Upload a document to detect sensitive information such as PII, internal project names, and financial data.",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(
                                onClick = {
                                    isProcessing = true
                                    coroutineScope.launch {
                                        delay(2000)
                                        showResult = true
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                            ) {
                                Icon(Icons.Outlined.UploadFile, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Upload & Scan")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LeakScanResultView() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDE0E0)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Outlined.Warning, contentDescription = null, tint = Color(0xFFD32F2F), modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Potential Leaks Found", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFFD32F2F))
                Text("We detected ${sampleLeaks.size} types of sensitive data in this document.", style = MaterialTheme.typography.bodyMedium, color = Color(0xFFD32F2F), textAlign = TextAlign.Center)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        
        sampleLeaks.forEach { leak ->
            LeakItemCard(leak)
            Spacer(modifier = Modifier.height(12.dp))
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { /* Redact Action */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Outlined.Security, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Auto-Redact All Findings")
        }
    }
}

@Composable
fun LeakItemCard(leak: LeakItem) {
    val (icon, color) = when (leak.severity) {
        "Critical" -> Icons.Outlined.GppBad to Color(0xFFD32F2F)
        "High" -> Icons.Outlined.GppMaybe to Color(0xFFFF9800)
        "Medium" -> Icons.Outlined.Info to Color(0xFFFBC02D)
        else -> Icons.Outlined.CheckCircleOutline to Color(0xFF4CAF50)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(leak.type, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(leak.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Badge(containerColor = color) {
                Text(leak.occurrences.toString(), color = Color.White)
            }
        }
    }
}
