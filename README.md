# Secret Key Generator

## Overview
The **Secret Key Generator** is a Java Swing application that allows users to generate cryptographic secret keys using different algorithms and key sizes. Users can copy the generated key to the clipboard, save it to a file, and toggle between light and dark themes.

## Features
- Generate secret keys using **HmacSHA256, HmacSHA512, and AES** algorithms.
- Select key sizes: **128-bit, 192-bit, and 256-bit**.
- Copy generated keys to the clipboard.
- Save generated keys to a text file.
- Toggle between **light and dark themes**.

## Prerequisites
To run this application, ensure you have:
- **Java 8 or later** installed.
- A compatible **JDK** with Swing support.

## How to Run
1. **Clone the repository** or download the source code.
2. **Compile the Java file**:
   ```sh
   javac -d . SecretKeyGeneratorApp.java
   ```
3. **Run the application**:
   ```sh
   java org.example.SecretKeyGeneratorApp
   ```

## Usage
1. Select the **algorithm** (HmacSHA256, HmacSHA512, AES).
2. Choose the **key size** (128-bit, 192-bit, or 256-bit).
3. Click **Generate Key** to create a new secret key.
4. Use the **Copy to Clipboard** button to copy the key.
5. Use the **Save to File** button to save the key.
6. Toggle between **dark mode and light mode** using the toggle button.

## File Structure
```
SecretKeyGenerator/
│── src/
│   └── org/example/SecretKeyGeneratorApp.java
│── README.md
```

## Error Handling
- If an **unsupported algorithm** is selected, an error message is displayed.
- If the **key field is empty**, copying or saving operations are prevented.
- If a **file name is empty**, saving is canceled with an alert.

## License
This project is **open-source** and can be modified and distributed freely.

## Author
LowkeyDev

