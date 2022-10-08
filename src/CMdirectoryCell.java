public class CMdirectoryCell {
    private boolean busy;
    private boolean dirty;
    private int tag;

    //Constructor--------------------------------------
    public CMdirectoryCell(){
        busy = false;
        dirty = false;
        tag = -1;
    }
    //-------------------------------------------------

    //Setters-------------------------------------------
    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void setTag(int tag_in) {
        //System.out.println("Changing tag to: " + tag_in);
        tag = tag_in;
        //System.out.println("Tag is now: " + tag);
    }
    //---------------------------------------------------

    //Getters--------------------------------------------
    public boolean isBusy() {
        return busy;
    }

    public boolean isDirty() {
        return dirty;
    }
    public int getTag() {
        return tag;
    }
    //---------------------------------------------------

    //setEverythingTo0()---------------------------------
    public void setEverythingTo0(){
        busy = false;
        dirty = false;
        tag = -1; //The word 0 could not be in CM
    }
    //---------------------------------------------------

    //compareTag(int tag)----------------------------
    public boolean compareTag(int in_tag){
        return tag == in_tag;
    }
    //---------------------------------------------------

    //toString-------------------------------------------
    public String toString(){
        return "busy: " + busy + "; dirty: " + dirty + "; tag: " + tag;
    }
    //---------------------------------------------------
}
