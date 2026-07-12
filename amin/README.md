<div align="center">
<img width="1200" height="475" alt="GHBanner" src="https://ai.google.dev/static/site-assets/images/share-ais-513315318.png" />
</div>

# PDFly - AI-Powered PDF Assistant

An Android application that leverages Google's Gemini AI to provide intelligent PDF processing capabilities including summarization, chat, comparison, and more.

## Features

- **PDF Summarization**: Get concise summaries of your PDF documents
- **Chat with PDF**: Ask questions and get answers from your PDF content
- **Document Comparison**: Compare multiple PDFs side by side
- **Processing Tools**: Various AI-powered tools for PDF manipulation
- **Secure & Private**: Your documents are processed securely

## Run Locally

**Prerequisites:**  [Android Studio](https://developer.android.com/studio)


1. Open Android Studio
2. Select **Open** and choose the directory containing this project
3. Allow Android Studio to fix any any incompatibilities as it imports the project.
4. Create a file named `.env` in the project directory and set `GEMINI_API_KEY` in that file to your Gemini API key (see `.env.example` for an example)
5. Remove this line from the app's `build.gradle.kts` file: `signingConfig = signingConfigs.getByName("debugConfig")`
6. Run the app on an emulator or physical device

## Project Structure

```
amin/
├── app/                      # Main application module
│   ├── src/
│   │   ├── main/            # Main source code
│   │   ├── test/            # Unit tests
│   │   └── androidTest/     # Instrumentation tests
│   └── build.gradle.kts     # App-level build configuration
├── assets/                   # Application assets
├── gradle/                   # Gradle wrapper and configuration
├── build.gradle.kts         # Project-level build configuration
├── settings.gradle.kts      # Gradle settings
└── README.md                # This file
```

## License

This project is provided as-is for educational and development purposes.
