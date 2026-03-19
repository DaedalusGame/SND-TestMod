package yourmod;

public class RecursionScope {
    int scopeNum;

    public boolean isOpen() {
        return scopeNum > 0;
    }

    public void open() {
        scopeNum++;
    }

    public void close() {
        if(scopeNum <= 0) {
            throw new RuntimeException("ruh roh raggy");
        }
        scopeNum--;
    }
}
