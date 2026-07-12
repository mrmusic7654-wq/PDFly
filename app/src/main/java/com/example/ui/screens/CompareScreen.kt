package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareScreen(navController: NavController, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("Compare PDFs", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FileCard("Original", "contract-v1.pdf", "2.1 MB · 12 pages", modifier = Modifier.weight(1f))
            FileCard("Revised", "contract-v2.pdf", "2.3 MB · 13 pages", modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📝 AI Analysis of Changes", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Found 7 changes across 4 pages.")
                
                Spacer(modifier = Modifier.height(16.dp))
                
                ChangeItem(
                    severity = "CRITICAL",
                    color = Color(0xFFD32F2F),
                    title = "Section 4.2",
                    description = "Non-compete clause ADDED.\n\"Employee shall not engage with any competing business within 50 miles for 24 months.\"",
                    warning = "This is a significant new legal restriction. Consult legal counsel before signing."
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                ChangeItem(
                    severity = "IMPORTANT",
                    color = Color(0xFFF57C00),
                    title = "Section 7.1",
                    description = "Compensation changed.\n\"\$85,000\" → \"\$92,000\" (↑8.2%)",
                    warning = "Bonus structure also modified (see details)."
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                ChangeItem(
                    severity = "MODERATE",
                    color = Color(0xFFFBC02D),
                    title = "Section 12.3",
                    description = "Notice period changed.\n\"2 weeks\" → \"4 weeks\""
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Download Diff Report")
        }
    }
}

@Composable
fun FileCard(label: String, name: String, details: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(name, fontWeight = FontWeight.Medium)
            Text(details, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            
            Spacer(modifier = Modifier.height(16.dp))
            // Mock PDF view
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
                    .background(Color.White, RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
fun ChangeItem(severity: String, color: Color, title: String, description: String, warning: String? = null) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).background(color, RoundedCornerShape(6.dp)))
            Spacer(modifier = Modifier.width(8.dp))
            Text("$severity — $title", fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(description, style = MaterialTheme.typography.bodyMedium)
        if (warning != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(warning, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
