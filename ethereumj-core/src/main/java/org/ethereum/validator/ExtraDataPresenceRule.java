package org.ethereum.validator;

import org.ethereum.core.BlockHeader;
import org.ethereum.util.FastByteComparisons;
import org.spongycastle.util.encoders.Hex;

/**
 * Created by Stan Reshetnyk on 26.12.16.
 */
public class ExtraDataPresenceRule extends BlockHeaderRule {

    public final byte[] data;

    public final boolean required;

    public ExtraDataPresenceRule(byte[] data, boolean required) {
        this.data = data;
        this.required = required;
    }

    @Override
    public ValidationResult validate(BlockHeader header) {
        if (required && !FastByteComparisons.equal(header.getExtraData(), data)) {
            return fault("Block " + header.getNumber() + " is no-fork. Expected presence of: " +
                    Hex.toHexString(data) + ", in extra data: " + Hex.toHexString(header.getExtraData()));
        } else if (!required && FastByteComparisons.equal(header.getExtraData(), data)) {
            return fault("Block " + header.getNumber() + " is pro-fork. Expected no: " +
                    Hex.toHexString(data) + ", in extra data: " + Hex.toHexString(header.getExtraData()));
        }
        return Success;
    }
}
