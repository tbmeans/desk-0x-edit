package com.timothybmeans.biz;

public class FileBytes {
    private int offset;
    private byte[] values;
    
    public FileBytes() {
        offset = -1;
        values = null;
    }

    public FileBytes(int offset, byte[] values) {
        this.offset = offset;
        this.values = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            this.values[i] = values[i];
        }
    }

    public int getOffset() {
		return offset;
	}

	public int getEndOffset() {
		return offset + values.length - 1;
    }
	
	public byte[] getValues() {
		return values;
	}

    public int getBufSize() {
        return values.length;
    }
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public void setValues(byte[] values) {
		this.values = new byte[values.length];
		for (int i = 0; i < values.length; i++) {
			this.values[i] = values[i];
		}
	}
}
