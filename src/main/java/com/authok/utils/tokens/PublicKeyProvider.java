package com.authok.utils.tokens;

import com.authok.exception.PublicKeyProviderException;

import java.security.interfaces.RSAPublicKey;

/**
 * The interface to obtain a public key. This is used to configure signature verification for tokens signed
 * with the RS256 asymmetric signing algorithm.
 * Developers should provide an implementation of this interface when verifying a RS256 ID token.
 *
 * <p>The following example demonstrates using the {@code JwkProviderBuilder} from the
 * <a href="https://github.com/authok/jwks-rsa-java">jwks-rsa-java library</a> to fetch the public key.</p>
 *
 * <pre>
 * JwkProvider provider = new JwkProviderBuilder("https://your-domain.authok.cn").build();
 * SignatureVerifier sigVerifier = SignatureVerifier.forRS256(new PublicKeyProvider() {
 *     &#064;Override
 *     public RSAPublicKey getPublicKeyById(String keyId) throws PublicKeyProviderException {
 *         try {
 *             return (RSAPublicKey) provider.get(keyId).getPublicKey();
 *         } catch (JwkException jwke) {
 *             throw new PublicKeyProviderException("Error obtaining public key", jwke);
 *         }
 *     }
 * }
 * </pre>
 */
public interface PublicKeyProvider {

    /**
     * Get a {@code RSAPublicKey} given the key ID.
     *
     * @param keyId the key ID for which to retrieve the key.
     * @return the {@code RSAPublicKey} for the given key ID.
     * @throws PublicKeyProviderException if the public key cannot be retrieved.
     */
    RSAPublicKey getPublicKeyById(String keyId) throws PublicKeyProviderException;
}
