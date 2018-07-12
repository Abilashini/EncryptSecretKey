# EncryptSecretKey

This client has been implemented to encrypt the plaintext secretkey to be used with WSO2 IS totp authenticator. 

## Steps to get it work,

1. Checkout the source and build it using the command

    `mvn clean install`
2. Copy the target/SecretKeyEncryptor.jar and conf/ folder into a same location.
3. Configure the config.properties file located inside the /conf folder with the relevant values and save it. 
	- CipherTransformation : by default IS 5.2.0 uses RSA. If you have configured OAEP in the IS, then change this value to - `RSA/ECB/OAEPwithSHA1andMGF1Padding`
	- Certificate : point to the certificate file
	- KeyType
	- KeyPassword
	- KeyAlias
4. Execute the jar in the terminal using the command,
	 java -jar SecretKeyEncryptor.jar
5. Enter the plaintext secret key
6. You will get an encrypted secret key

    Example:
    ```
      Enter the secret key: AAHFSCE4GIIII6BO
      Encrypted secretkey: AM4bAyELLFOpL399bCJJdDm5LbdrGEbbxQH6Ve+DC1Oaii3DXBxn04EKGD0MfGrJ5I4E+ZOc+TudoaeoTeRfbMeogq5RjZQZZmMaPforEDCQeAmGyNC3TwriVCfiESTrpFtKGSqXFgJXsTE8eNN/0i1MQex32oe6Du19yrfGDRk=
    ```  
7. Update the user profile with the above encrypted secret key added to the 'Secret Key' claim
