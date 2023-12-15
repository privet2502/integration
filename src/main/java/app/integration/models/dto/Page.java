package app.integration.models.dto;

public class Page {

    private long num;
    private long offset;
    private boolean active;

    public Page(long num, long offset, boolean active) {
        this.num = num;
        this.offset = offset;
        this.active = active;
    }

    public long getNum() {
        return this.num;
    }

    public long getOffset() {
        return this.offset;
    }

    public boolean isActive() {
        return this.active;
    }
}