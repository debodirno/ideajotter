package xenophobe.applications.com.ideasjotter;

/**
 * Created by Debodirno on 05-Mar-16.
 */
public class FileName {
    private String shorttext;
    private String name;

    public FileName(String _name, String _shorttext){
        this.name = _name;
        this.shorttext = _shorttext;
    }

    public void setName(String _name){
        this.name = _name;
    }

    public String getName(){
        return this.name;
    }

    public void setShorttext(String _shorttext){
        this.shorttext = _shorttext;
    }

    public String getShorttext(){
        return this.shorttext;
    }
}
