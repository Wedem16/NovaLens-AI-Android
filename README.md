# NovaLens AI

A free Android camera/gallery starter app built with Java.

## Build a downloadable APK with GitHub Actions
1. Create a public repository named `NovaLens-AI-Android`.
2. Upload the *contents* of this folder to the repository root (not this ZIP file).
3. Commit the files to the `main` branch.
4. Open the **Actions** tab and choose **Build NovaLens AI APK**.
5. Run the workflow if it did not start automatically.
6. When the run succeeds, download the `NovaLens-debug-apk` artifact and extract it to get `app-debug.apk`.

## Included in this starter
- Opens the device's installed camera app.
- Lets the user select a picture from the gallery and previews it.
- No paid API key, ads, or subscription code.

## Not included yet
AI zoom, AI photo enhancement, object recognition, and a custom in-app camera pipeline are not implemented in this starter.

## Project structure
- `settings.gradle`
- `build.gradle`
- `gradle.properties`
- `app/build.gradle`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/ai/novalens/free/MainActivity.java`
- `.github/workflows/build-apk.yml`
