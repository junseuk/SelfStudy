class UnsupportedFunctionException extends RuntimeException {
	final private int ERR_CODE;
	UnsupportedFunctionException(String msg, int errCode) {
		super(msg);
		ERR_CODE = errCode;
	}

	public int getErrorCode() {
		return ERR_CODE;
	}

	public String getMessage() {
		return super.getMessage() + "[" + getErrorCode() + "]";
	}
}

public class Q8_9 {
	public static void main(String args[]) {
		throw new UnsupportedFunctionException("not supported function.", 100);
	}
}
