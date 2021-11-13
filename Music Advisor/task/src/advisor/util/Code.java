package advisor.util;

public class Code {
    private volatile String code;

    public String getCode() {
        if (isCodeReceived()) {
            return this.code;
        } else {
            throw new IllegalStateException("Code isn't received");
        }
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCodeReceived() {
        return this.code != null;
    }
}
