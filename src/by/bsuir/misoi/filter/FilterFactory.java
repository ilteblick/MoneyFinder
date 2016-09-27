package by.bsuir.misoi.filter;

/**
 * Created by User-PC on 21.09.2016.
 */
public class FilterFactory {
    public IFilter createFilter(int tmp){
        switch (tmp){
            case 1: return new Binarization();
            case 2: return new Median();
            default: return null;
        }
    };

}
