import jssc.SerialPortException;

public class Filter {
    //tæller som Data Transfer Object - her overføres data til andre dele af programmet

    private static Filter FilterOBJ = new Filter();

    private Filter(){
    }

    public static Filter getFilterOBJ(){
        return FilterOBJ;
    }

    static int ValueA[] = new int[3950];

    static int ValueB[] = new int[3950];//svarer til 5 sekunder
    int d, h = 0;
    String buffer = "";

    public void filter3950measurements(int[] intArray) {
        String[] stringArray;
        while (d < 3950) {
            String s = SerialPortClass.getSerialPortOBJ().maaling();
            if (s != null) {
                buffer = buffer + s;
                int i = buffer.indexOf("A");
                if (i > -1) {
                    stringArray = buffer.split("A");
                    if (stringArray != null && stringArray.length > 0) {
                        if (buffer.charAt(buffer.length() - 1) != 65) {
                            buffer = stringArray[stringArray.length - 1];
                        } else {
                            buffer = "";
                        }

                        while (h < stringArray.length - 1 && stringArray.length > 1) {
                            if (stringArray[h] != null) {
                                if ((d + h) >= 3950) {
                                    break;
                                }
                                intArray[d + h] = Integer.parseInt(stringArray[h]);
                            }
                            h++;
                        }
                        d = d + h;
                        h = 0;
                    }
                }
            }
        }
        d = 0;

    }
}

