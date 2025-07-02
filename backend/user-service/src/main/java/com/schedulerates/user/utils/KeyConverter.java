package com.schedulerates.user.utils;

import lombok.experimental.UtilityClass;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Utility class for converting PEM-encoded keys to {@link PublicKey} and {@link PrivateKey} objects.
 * Provides methods to parse and convert PEM-encoded public and private key strings into their respective Java security key representations.
 */
@UtilityClass
public class KeyConverter {

    public PublicKey convertPublicKey(final String publicPemKey) {
        try (PEMParser pemParser = new PEMParser(new StringReader(publicPemKey))) {
            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
            return new JcaPEMKeyConverter().getPublicKey(publicKeyInfo);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public PrivateKey convertPrivateKey(final String privatePemKey) {
        try (PEMParser pemParser = new PEMParser(new StringReader(privatePemKey))) {
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());
            return new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
