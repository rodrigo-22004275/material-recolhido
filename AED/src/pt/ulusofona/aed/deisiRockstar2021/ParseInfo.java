package pt.ulusofona.aed.deisiRockstar2021;

public class ParseInfo {
    int ok = 0;
    int ignoradas = 0;

    public ParseInfo() { }

    public ParseInfo(int ok, int ignoradas) {
        this.ok = ok;
        this.ignoradas = ignoradas;
    }

    public String toString() {
        return "OK: " + ok + ", Ignored: " + ignoradas;
    }
}
