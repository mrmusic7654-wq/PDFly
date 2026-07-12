package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.automirrored.outlined.CompareArrows
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, innerPadding: PaddingValues) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "PDFly",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Person, contentDescription = "Account")
                }
                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Drop zone
        DropZoneCard()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    height = 3.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            divider = {}
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("⚡ Basic Tools", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("🤖 AI Tools", fontWeight = FontWeight.Bold) }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (selectedTab == 0) {
            ToolsGrid(tools = basicTools, navController = navController)
        } else {
            ToolsGrid(tools = aiTools, navController = navController)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("📁 Recent Files", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        RecentFilesList()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Upgrade banner
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "💡 You're on the Free plan. 3 AI operations remaining today.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Upgrade to Pro — $7.99/month")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(80.dp))
        }
        
        FloatingActionButton(
            onClick = { navController.navigate("chatbot_help") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .size(64.dp),
            containerColor = Color(0xFF673AB7),
            contentColor = Color.White,
            shape = RoundedCornerShape(20.dp)
        ) {
            Icon(Icons.Filled.SmartToy, contentDescription = "Chat with AI", modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun DropZoneCard() {
    Card(
        modifier = Modifier.fillMaxWidth().height(220.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.img_hero_banner_1783752890169),
                contentDescription = "Drop Zone Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.6f
            )
            // Overlay gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.InsertDriveFile, 
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Ready to work? Drop your PDF here", 
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Or paste a URL", 
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Free users: 10MB per file · Pro: 100MB",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

data class Tool(val name: String, val icon: ImageVector, val route: String?, val tint: Color)

val basicTools = listOf(
    Tool("Merge", Icons.Outlined.Link, "merge", Color(0xFF4285F4)),
    Tool("Split", Icons.Outlined.ContentCut, "split", Color(0xFFEA4335)),
    Tool("Compress", Icons.Outlined.Compress, "compress", Color(0xFFFBBC05)),
    Tool("Convert", Icons.Outlined.Sync, "convert", Color(0xFF34A853)),
    Tool("To PDF", Icons.Outlined.ArrowForward, "topdf", Color(0xFF8E24AA)),
    Tool("Edit", Icons.Outlined.Edit, "edit", Color(0xFF3F51B5)),
    Tool("Expand", Icons.Outlined.OpenWith, "expand", Color(0xFF00ACC1)),
    Tool("Protect", Icons.Outlined.Lock, "protect", Color(0xFF607D8B)),
    Tool("PDF to Word", Icons.Outlined.Article, "pdf_to_word", Color(0xFF2B579A)),
    Tool("PDF to Excel", Icons.Outlined.TableView, "pdf_to_excel", Color(0xFF217346)),
    Tool("Word to PDF", Icons.Outlined.TextSnippet, "word_to_pdf", Color(0xFF2B579A)),
    Tool("Excel to PDF", Icons.Outlined.ViewList, "excel_to_pdf", Color(0xFF217346)),
    Tool("PDF to PPT", Icons.Outlined.PresentToAll, "pdf_to_ppt", Color(0xFFD24726)),
    Tool("PPT to PDF", Icons.Outlined.CoPresent, "ppt_to_pdf", Color(0xFFD24726))
)

val aiTools = listOf(
    Tool("Summarize", Icons.Outlined.Subject, "summarize", Color(0xFF673AB7)),
    Tool("Chat AI", Icons.Outlined.ChatBubbleOutline, "chat", Color(0xFF009688)),
    Tool("Extract", Icons.Outlined.GridView, "extract", Color(0xFFE91E63)),
    Tool("Translate", Icons.Outlined.Translate, "translate", Color(0xFF00BCD4)),
    Tool("Compare", Icons.AutoMirrored.Outlined.CompareArrows, "compare", Color(0xFFFF9800)),
    Tool("Smart OCR", Icons.Outlined.DocumentScanner, "ocr", Color(0xFF4CAF50)),
    Tool("ELI5 Mode", Icons.Outlined.Face, "eli5", Color(0xFFFFC107)),
    Tool("Tone Mapper", Icons.Outlined.Mood, "tone_mapper", Color(0xFFE91E63)),
    Tool("Contradict", Icons.Outlined.Gavel, "contradict", Color(0xFF3F51B5)),
    Tool("To Podcast", Icons.Outlined.Mic, "podcast", Color(0xFF00BCD4)),
    Tool("Red Team", Icons.Outlined.Security, "red_team", Color(0xFFD32F2F)),
    Tool("Argue Map", Icons.Outlined.AccountTree, "argue_map", Color(0xFF8E24AA)),
    Tool("Time Machine", Icons.Outlined.History, "time_machine", Color(0xFF795548)),
    Tool("Hallucination", Icons.Outlined.Troubleshoot, "hallucination", Color(0xFFF44336)),
    Tool("Ghostwriter", Icons.Outlined.PersonSearch, "ghostwriter", Color(0xFF607D8B)),
    Tool("Memory Vault", Icons.Outlined.Storage, "memory_vault", Color(0xFF9C27B0)),
    Tool("Leak Scanner", Icons.Outlined.PrivacyTip, "leak_scanner", Color(0xFFE53935)),
    Tool("What's Missing", Icons.Outlined.QuestionMark, "whats_missing", Color(0xFFFF5722)),
    Tool("Sign Analyzer", Icons.Outlined.Draw, "sign_analyzer", Color(0xFF3F51B5)),
    Tool("PDF to Code", Icons.Outlined.Code, "pdf_to_code", Color(0xFF009688)),
    Tool("Deep Fake", Icons.Outlined.NoPhotography, "deep_fake", Color(0xFFE91E63)),
    Tool("Reading Race", Icons.Outlined.Speed, "reading_race", Color(0xFF4CAF50)),
    Tool("Doc Battle", Icons.Outlined.SportsMartialArts, "doc_battle", Color(0xFFFF9800)),
    Tool("Escape Room", Icons.Outlined.Key, "escape_room", Color(0xFF673AB7))
)

@Composable
fun ToolsGrid(tools: List<Tool>, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val chunkedTools = tools.chunked(3)
        chunkedTools.forEach { rowTools ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowTools.forEach { tool ->
                    Box(modifier = Modifier.weight(1f)) {
                        ToolItem(tool) {
                            if (tool.route != null) {
                                navController.navigate(tool.route)
                            }
                        }
                    }
                }
                val remaining = 3 - rowTools.size
                repeat(remaining) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ToolItem(tool: Tool, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                            colors = listOf(
                                tool.tint.copy(alpha = 0.25f),
                                tool.tint.copy(alpha = 0.05f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    tool.icon, 
                    contentDescription = tool.name,
                    tint = tool.tint,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                tool.name, 
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                minLines = 2,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RecentFilesList() {
    Column(modifier = Modifier.fillMaxWidth()) {
        RecentFileItem("contract-v2.pdf", "10 min ago", "Summarized")
        RecentFileItem("report-2025.pdf", "2 hours ago", "Tables extracted")
        RecentFileItem("invoice-march.pdf", "yesterday", "Compressed")
    }
}

@Composable
fun RecentFileItem(name: String, time: String, action: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.AutoMirrored.Filled.InsertDriveFile,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.Medium)
            Text("$time · $action", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(Icons.Default.MoreVert, contentDescription = "More")
    }
}
