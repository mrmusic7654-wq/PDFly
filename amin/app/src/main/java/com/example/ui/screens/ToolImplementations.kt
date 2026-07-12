package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.R

@Composable
fun MergeScreen(navController: NavController, innerPadding: PaddingValues) {
    ProcessingToolScreen(
        navController = navController,
        innerPadding = innerPadding,
        title = "Merge PDFs",
        description = "Combine multiple PDFs into a single document.",
        actionLabel = "Merge Now",
        icon = Icons.Outlined.Link,
        multipleFiles = true,
        bgImageRes = R.drawable.premium_tool_bg,
        successMessage = "Merged successfully! Output size: 7.1 MB"
    )
}

@Composable
fun CompressScreen(navController: NavController, innerPadding: PaddingValues) {
    var level by remember { mutableStateOf("Recommended") }
    ProcessingToolScreen(
        navController = navController,
        innerPadding = innerPadding,
        title = "Compress PDF",
        description = "Reduce file size while maintaining quality.",
        actionLabel = "Compress PDF",
        icon = Icons.Outlined.Compress,
        bgImageRes = R.drawable.premium_tool_bg,
        successMessage = "Compressed successfully! New size: 1.2 MB (Saved 50%)"
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Compression Level", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                listOf("Extreme (Smallest size, lowest quality)", "Recommended (Good balance)", "Less (High quality, larger size)").forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = option.startsWith(level),
                            onClick = { level = option.split(" ").first() }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(option, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun SplitScreen(navController: NavController, innerPadding: PaddingValues) {
    var range by remember { mutableStateOf("") }
    ProcessingToolScreen(
        navController = navController,
        innerPadding = innerPadding,
        title = "Split PDF",
        description = "Extract specific pages from your PDF.",
        actionLabel = "Split PDF",
        icon = Icons.Outlined.ContentCut,
        bgImageRes = R.drawable.premium_tool_bg,
        successMessage = "Split successfully! Created 1 new document."
    ) {
        OutlinedTextField(
            value = range,
            onValueChange = { range = it },
            label = { Text("Page Range (e.g., 1-5, 8, 11-13)") },
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun ProtectScreen(navController: NavController, innerPadding: PaddingValues) {
    var password by remember { mutableStateOf("") }
    ProcessingToolScreen(
        navController = navController,
        innerPadding = innerPadding,
        title = "Protect PDF",
        description = "Add a password to secure your document.",
        actionLabel = "Add Password",
        icon = Icons.Outlined.Lock,
        bgImageRes = R.drawable.bg_security,
        successMessage = "Password protection applied successfully."
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun ConvertScreen(navController: NavController, innerPadding: PaddingValues, toPdf: Boolean) {
    var format by remember { mutableStateOf(if (toPdf) "Word (.docx)" else "Word (.docx)") }
    val title = if (toPdf) "Convert to PDF" else "Convert PDF"
    val desc = if (toPdf) "Convert Word, Excel, PPT, or Images to PDF." else "Convert your PDF to editable formats."
    
    val options = if (toPdf) {
        listOf("Word (.docx)", "Excel (.xlsx)", "PowerPoint (.pptx)", "Image (.jpg)")
    } else {
        listOf("Word (.docx)", "Excel (.xlsx)", "PowerPoint (.pptx)", "Image (.jpg)")
    }
    
    ProcessingToolScreen(
        navController = navController,
        innerPadding = innerPadding,
        title = title,
        description = desc,
        actionLabel = "Convert",
        icon = if (toPdf) Icons.Outlined.ArrowForward else Icons.Outlined.Sync,
        bgImageRes = R.drawable.bg_convert,
        successMessage = "Conversion completed successfully."
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(if (toPdf) "Source Format" else "Target Format", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = format == option,
                            onClick = { format = option }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(option, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun DefaultActionScreen(
    navController: NavController, 
    innerPadding: PaddingValues, 
    title: String, 
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    bgImageRes: Int = R.drawable.premium_tool_bg
) {
    ProcessingToolScreen(
        navController = navController,
        innerPadding = innerPadding,
        title = title,
        description = "Process your document using the $title tool.",
        actionLabel = title,
        icon = icon,
        bgImageRes = bgImageRes,
        successMessage = "Operation completed successfully."
    )
}
