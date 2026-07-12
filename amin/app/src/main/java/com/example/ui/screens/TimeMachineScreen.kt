package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class VersionInfo(val id: String, val author: String, val timestamp: String)
val sampleVersions = listOf(
    VersionInfo("v1.2", "Alice (Legal)", "Oct 12, 14:30"),
    VersionInfo("v1.1", "Bob (Sales)", "Oct 12, 09:15"),
    VersionInfo("v1.0", "System", "Oct 11, 18:00")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeMachineScreen(navController: NavController, innerPadding: PaddingValues) {
    var isProcessing by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (showResult) "Version History" else "PDF Time Machine") },
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
            label = "TimeMachineTransition"
        ) { isResult ->
            if (isResult) {
                TimeMachineResultView()
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
                            CircularProgressIndicator(modifier = Modifier.size(64.dp), color = Color(0xFF795548))
                            Spacer(modifier = Modifier.height(24.dp))
                            Text("Reconstructing document history...", style = MaterialTheme.typography.titleMedium)
                        } else {
                            Icon(
                                Icons.Outlined.History,
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                tint = Color(0xFF795548)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                "Upload a document to reveal its invisible edit history, author changes, and structural evolution.",
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
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548))
                            ) {
                                Icon(Icons.Outlined.UploadFile, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Upload & Analyze Metadata")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimeMachineResultView() {
    var selectedVersion by remember { mutableStateOf(sampleVersions[0]) }
    
    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = sampleVersions.indexOf(selectedVersion),
            edgePadding = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            sampleVersions.forEach { version ->
                Tab(
                    selected = selectedVersion == version,
                    onClick = { selectedVersion = version },
                    text = { Text("${version.id}\n${version.timestamp}", textAlign = TextAlign.Center) }
                )
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Person, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edited by: ${selectedVersion.author}", fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Diff Analysis:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Text(
                    text = getDiffText(selectedVersion.id),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

fun getDiffText(versionId: String) = buildAnnotatedString {
    when (versionId) {
        "v1.2" -> {
            append("The contract shall be binding upon all parties. ")
            withStyle(SpanStyle(background = Color(0xFFC8E6C9), color = Color(0xFF1B5E20))) {
                append("Except as otherwise specified in Addendum B.")
            }
            append(" Payments are due ")
            withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough, background = Color(0xFFFFCDD2), color = Color(0xFFB71C1C))) {
                append("net 30")
            }
            withStyle(SpanStyle(background = Color(0xFFC8E6C9), color = Color(0xFF1B5E20))) {
                append(" net 45")
            }
            append(" days.")
        }
        "v1.1" -> {
            append("The contract shall be binding upon all parties. ")
            withStyle(SpanStyle(background = Color(0xFFC8E6C9), color = Color(0xFF1B5E20))) {
                append("Payments are due net 30 days.")
            }
        }
        else -> {
            append("The contract shall be binding upon all parties. Payments are due upon receipt.")
        }
    }
}
