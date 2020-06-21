public class Package {
    public class Header{
        public int size;
    }
    Header header = new Header();
    byte[] packageData;
    Package(byte[] packageData){
        this.packageData = packageData;
        this.header.size = packageData.length;
    }
    public Header getHeader(){
        return header;
    }

    public byte[] getPackageData() {
        return packageData;
    }
}
