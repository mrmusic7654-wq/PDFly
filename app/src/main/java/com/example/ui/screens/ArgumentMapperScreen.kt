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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Data Models
enum class NodeType(val color: Color, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    MAIN_CLAIM(Color(0xFF3F51B5), Icons.Outlined.Flag),
    SUPPORTING_EVIDENCE(Color(0xFF4CAF50), Icons.Outlined.CheckCircle),
    COUNTER_ARGUMENT(Color(0xFFFF9800), Icons.Outlined.Warning),
    FALLACY(Color(0xFFF44336), Icons.Outlined.ErrorOutline),
    MISSING_EVIDENCE(Color(0xFF9E9E9E), Icons.Outlined.HelpOutline)
}

data class ArgNode(
    val title: String,
    val description: String,
    val type: NodeType,
    val children: List<ArgNode> = emptyList()
)

val sampleArgumentTree = ArgNode(
    title = "Main Claim: Move to 4-Day Work Week",
    description = "The company should transition to a 4-day work week to improve productivity and employee satisfaction.",
    type = NodeType.MAIN_CLAIM,
    children = listOf(
        ArgNode(
            title = "Evidence: Productivity Studies",
            description = "Multiple studies show a 20% increase in per-hour productivity.",
            type = NodeType.SUPPORTING_EVIDENCE,
            children = listOf(
                ArgNode(
                    title = "Fallacy: Correlation vs Causation",
                    description = "Assumes productivity increase is solely due to hours reduction, ignoring concurrent tool upgrades.",
                    type = NodeType.FALLACY
                )
            )
        ),
        ArgNode(
            title = "Counterargument: Coverage Gaps",
            description = "Customer support requires 24/5 coverage, which becomes difficult.",
            type = NodeType.COUNTER_ARGUMENT,
            children = listOf(
                ArgNode(
                    title = "Missing Evidence",
                    description = "No data provided on current customer support volume during Fridays.",
                    type = NodeType.MISSING_EVIDENCE
                )
            )
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArgumentMapperScreen(navController: NavController, innerPadding: PaddingValues) {
    var isProcessing by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (showResult) "Argument Map" else "Argument Mapper") },
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
            label = "ArgMapTransition"
        ) { isResult ->
            if (isResult) {
                ArgumentMapResultView()
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
                            CircularProgressIndicator(modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(24.dp))
                            Text("Analyzing logic and claims...", style = MaterialTheme.typography.titleMedium)
                        } else {
                            Icon(
                                Icons.Outlined.AccountTree,
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                "Upload a persuasive document to visualize the logical structure of its claims, evidence, and fallacies.",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(
                                onClick = {
                                    isProcessing = true
                                    coroutineScope.launch {
                                        delay(2500)
                                        showResult = true
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(56.dp)
                            ) {
                                Icon(Icons.Outlined.UploadFile, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Upload & Analyze PDF")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArgumentMapResultView() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text("Logical Flowchart", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        ArgNodeView(node = sampleArgumentTree)
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ArgNodeView(node: ArgNode) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = node.type.color.copy(alpha = 0.1f)),
            border = androidx.compose.foundation.BorderStroke(1.dp, node.type.color.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(node.type.icon, contentDescription = null, tint = node.type.color, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(node.title, style = MaterialTheme.typography.titleMedium, color = node.type.color, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(node.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
        
        if (node.children.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Spacer(modifier = Modifier.width(24.dp))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
                
                Column(modifier = Modifier.weight(1f).padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) {
                    node.children.forEachIndexed { index, child ->
                        ArgNodeView(node = child)
                        if (index < node.children.size - 1) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
