// 63180016

public class Datum {
    int dan;
    int mesec;
    int leto;

    public Datum(int dan, int mesec, int leto){
        this.dan = dan;
        this.mesec = mesec;
        this.leto = leto;
    }

    public int vrniDan(){
        return this.dan;
    }
    
    public int vrniMesec(){
        return this.mesec;
    }

    public int vrniLeto(){
        return this.leto;
    }

    public String toString(){
        return String.format("%02d.%02d.%04d", this.dan, this.mesec, this.leto);
    }

    public boolean jeEnakKot(Datum datum){
        return leto == datum.leto && 
                mesec == datum.mesec &&
                dan == datum.dan; 
    }
}