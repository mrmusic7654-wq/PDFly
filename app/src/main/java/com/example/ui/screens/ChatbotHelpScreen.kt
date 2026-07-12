package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotHelpScreen(navController: NavController, innerPadding: PaddingValues) {
    var messages by remember { mutableStateOf(listOf(
        Message("Hello! I am your PDFly Assistant. How can I help you use the tools today?", false)
    )) }
    var inputText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var isTyping by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Image(
            painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.bg_ai),
            contentDescription = null,
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.7f
        )
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f))
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text("PDFly Assistant", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            
            // Chat area
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(messages) { message ->
                    ChatBubble(message)
                }
                if (isTyping) {
                    item {
                        TypingIndicator()
                    }
                }
            }
            
            // Input area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Ask about any tool...", color = Color.White.copy(alpha = 0.7f)) },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedBorderColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                FloatingActionButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            val newMsg = inputText
                            messages = messages + Message(newMsg, true)
                            inputText = ""
                            isTyping = true
                            scope.launch {
                                listState.animateScrollToItem(messages.size)
                                delay(1500)
                                
                                val response = getBotResponse(newMsg)
                                isTyping = false
                                messages = messages + Message(response, false)
                                listState.animateScrollToItem(messages.size)
                            }
                        }
                    },
                    modifier = Modifier.size(56.dp),
                    containerColor = Color(0xFF673AB7),
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}

fun getBotResponse(query: String): String {
    val lowerQuery = query.lowercase()
    return when {
        "merge" in lowerQuery -> "The Merge tool allows you to combine multiple PDF files into a single document. Simply select the files you want to combine in the correct order."
        "split" in lowerQuery -> "The Split tool lets you extract specific pages from a PDF or separate it into multiple documents by page range."
        "compress" in lowerQuery -> "The Compress tool reduces the file size of your PDF, making it easier to share via email or web without losing significant quality."
        "argue" in lowerQuery || "map" in lowerQuery -> "The Argue Map visually parses your document to create a logical flowchart of claims, evidence, and potential fallacies."
        "leak" in lowerQuery || "scan" in lowerQuery -> "The Leak Scanner analyzes your document for sensitive data like Social Security Numbers, credit cards, or internal project names before sharing."
        "time" in lowerQuery || "machine" in lowerQuery -> "The PDF Time Machine reveals the invisible edit history of your document, showing you previous authors and their changes."
        "hallucination" in lowerQuery -> "The Hallucination Detector cross-references factual claims within your AI-generated PDF to ensure they are logically consistent and backed by evidence."
        "ghostwriter" in lowerQuery -> "Ghostwriter Forensics determines if different parts of your PDF were authored by different people or AI agents based on linguistic style."
        else -> "You can explore tools from the dashboard! We have basic tools like Merge, Split, and Compress, as well as AI-powered tools like Argue Map, Leak Scanner, and Hallucination Detector. Feel free to ask about a specific one!"
    }
}

@Composable
fun TypingIndicator() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.SmartToy, 
                    contentDescription = "Bot",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text("Typing...", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
