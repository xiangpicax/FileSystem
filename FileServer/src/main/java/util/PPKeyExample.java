package util;


import java.util.UUID;

public class PPKeyExample {

    public static void main(String[] args) {
        jdkRSA();
    }
    // jdk实现：
    public static void jdkRSA() {
        try {
String strPublicKey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKT+MW8Bs1VgwjdZ4FNQ++hX0RTd1IFQlHWfuox35UP61Cnh+rmsIe7COZYLEfJf+6GphJrcWNil9xI6NMOuHbsCAwEAAQ==";
String strPrivateKey="MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEApP4xbwGzVWDCN1ngU1D76FfRFN3UgVCUdZ+6jHflQ/rUKeH6uawh7sI5lgsR8l/7oamEmtxY2KX3Ejo0w64duwIDAQABAkEAhc40Dt9lLZ9rwnHcEBWERdg6Et8YAMt1qrKgGlz9jJOj3RyajzUrpHDuxzYJRSngMSfTkuB+RnYVXpxsYQVJAQIhAPKXQjTYKhnzJbVRO0jcnhGBdol1FuxA+tDQCLhZqsCBAiEArhzl7xBrJx/Vqka5ijZbGMAyzduoOFnvyZjtfA9zwDsCIDEFJai+AYvq2TTRbFRl2D8bVROjqHUnEEe/sfNmkx2BAiEAnGEcK4HFm9g94bewAXEclLhbaQV2q1YrxhdyhVCjl+0CIQDvJ7V8owyGM7Rn77AZrT3HDQWRVqJVP8F9RzgeQdjcmQ==";
          PublicKeyUtils pku = new PublicKeyUtils();

          String result = pku.encryptByPublicKey(strPublicKey,UUID.randomUUID().toString());
         PrivateKeyUtils prku = new PrivateKeyUtils();
         String result2 =  prku.decryptionByPrivateKey(strPrivateKey, result);
         System.out.println("加密信息："+result);
         System.out.println("解密信息："+result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
