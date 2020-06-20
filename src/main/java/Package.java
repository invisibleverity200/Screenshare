public class Package {
    byte[] packageData;
    Package(byte[] packageData){
        this.packageData = packageData;
    }
    public int getSize(){
        return packageData.length;
    }

    public byte[] getPackageData() {
        return packageData;
    }
}
