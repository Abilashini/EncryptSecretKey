/*
 *
 *   Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 * /
 */
package org.wso2.secretkeyencryptor;

import org.apache.axiom.om.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Properties;
import java.util.Scanner;

public class SecretKeyEncryptor {
    public static void main(String[] args) throws Exception {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the secret key: ");
        String plaintext = scanner.next();

        Properties properties = new Properties();
        InputStream propertiesFile = new FileInputStream("conf/config.properties");
        properties.load(propertiesFile);

        String algorithm = properties.getProperty("CipherTransformation");
        String file = properties.getProperty("Certificate");

        String keyType = properties.getProperty("KeyType");
        String keyPassword = properties.getProperty("KeyPassword");
        String keyAlias = properties.getProperty("KeyAlias");

        KeyStore keyStore = KeyStore.getInstance(keyType);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            keyStore.load(in, keyPassword.toCharArray());
        } finally {
            if (in != null) {
                in.close();
                propertiesFile.close();
            }
        }

        Certificate[] certs = keyStore.getCertificateChain(keyAlias);
        Cipher cipher = Cipher.getInstance(algorithm, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, certs[0].getPublicKey());
        String ciphertext = Base64.encode(cipher.doFinal(plaintext.getBytes()));
        System.out.println("Encrypted secretkey: " + ciphertext);
    }
}
