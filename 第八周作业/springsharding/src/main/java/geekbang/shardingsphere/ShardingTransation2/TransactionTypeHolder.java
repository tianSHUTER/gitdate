package geekbang.shardingsphere.ShardingTransation2;

import org.apache.shardingsphere.transaction.core.TransactionType;

public class TransactionTypeHolder {

    private static final ThreadLocal<TransactionType> CONTEXT = new ThreadLocal<TransactionType>() {

        @Override
        protected TransactionType initialValue() {
            return TransactionType.LOCAL;
        }
    };

    /**
     * Get transaction type for current thr
     * ead.
     *
     * @return transaction type
     */
    public static TransactionType get() {
        return CONTEXT.get();
    }

    /**
     * Set transaction type for current thread.
     *
     * @param transactionType transaction type
     */
    public static void set(final TransactionType transactionType) {
        CONTEXT.set(transactionType);
    }

    /**
     * Clear transaction type for current thread.
     */
    public static void clear() {
        CONTEXT.remove();
    }}

