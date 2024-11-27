/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package point.of.sale.console.application;

/**
 *
 * @author Admin
 */

// This class is the blueprint of products it's responsible for manipulating a products
public class Product {
    
    public String productCode;
    private String productName;
    private int productStocks;
    private float productPrice;
    private Auth productDependency;
    
    Product () {
        
    }
    
   public void productsList(Auth dependency) {
       
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("|    No.    |    Code    |        Name        |    Stocks    |    Price    |");
        System.out.println("---------------------------------------------------------------------------");

        for (Product product : Storage.Products) {
            int productNo = Storage.Products.indexOf(product) + 1;
            if (product.productDependency.getUsername().equals(dependency.getUsername())) {
                System.out.printf(
                    "| %-8d | %-10s | %-18s | %-12d | %-10.1f |\n",
                    productNo, 
                    product.getCode(), 
                    product.getName(), 
                    product.getStocks(), 
                    product.getPrice()
                );
            }
        }
        System.out.println("---------------------------------------------------------------------------");
        
}

    public boolean setName(String name) {
        
        boolean isSucess = false;
        boolean isLoopExecute = false;
        for (Product product : Storage.Products) {
            isLoopExecute = true;
            if (product.getName().toLowerCase().equals(name.toLowerCase())) {
                isSucess = false;
                break;
            } else {
                isSucess = true;
            }
        }
                
        if (!isLoopExecute && !isSucess) {
            isSucess = true;
        }
        
        if (isSucess) {
             this.productName = name;
        }
       
        return isSucess;
    }

    public boolean setStocks(int stocks) {
        this.productStocks = stocks;
        return true;
    }

    public boolean setPrice(float price) {
        this.productPrice = price;
         return true;
    }

    public String setCode(Product product) {
        
        char firstLetter = product.getName().charAt(0);
        int currentNo = 0;
        int index = Storage.Products.indexOf(product);
        
        for ( Product item : Storage.Products ) {
            
            if (item.productCode == null) {
                break;
            } else if (item.getCode().charAt(0) == firstLetter) {
                int itemCodeNo = item.getCode().charAt(1) - '0';
                if (itemCodeNo > currentNo) {
                    currentNo = itemCodeNo;
                }
            }
            
        }
        
        String productCode = String.format("%c%d", firstLetter, currentNo + 1);
        Storage.Products.get(index).productCode = productCode;
        
        return productCode;
    }

    public void setDependency(Auth dependency) {
        this.productDependency = dependency;
    }
    
    public void searchByCode(String code, Auth dependency) {
        
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("|    No.    |    Code    |        Name        |    Stocks    |    Price    |");
        System.out.println("---------------------------------------------------------------------------");

        for (Product product : Storage.Products) {
            int productNo = Storage.Products.indexOf(product) + 1;
            if (product.productCode.equals(code) && product.productDependency.getUsername().equals(dependency.getUsername())) {
                System.out.printf(
                    "| %-8d | %-10s | %-18s | %-12d | %-10.1f |\n",
                    productNo, 
                    product.getCode(), 
                    product.getName(), 
                    product.getStocks(), 
                    product.getPrice()
                );
            }
        }
        System.out.println("---------------------------------------------------------------------------");
        
    }
    
    public void searchByName(String name, Auth dependency) {
        
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("|    No.    |    Code    |        Name        |    Stocks    |    Price    |");
        System.out.println("---------------------------------------------------------------------------");

        for (Product product : Storage.Products) {
            int productNo = Storage.Products.indexOf(product) + 1;
            if (product.getName().toLowerCase().equals(name.toLowerCase()) && product.productDependency.getUsername().equals(dependency.getUsername())) {
                System.out.printf(
                    "| %-8d | %-10s | %-18s | %-12d | %-10.1f |\n",
                    productNo, 
                    product.getCode(), 
                    product.getName(), 
                    product.getStocks(), 
                    product.getPrice()
                );
            }
        }
        System.out.println("---------------------------------------------------------------------------");
        
    }
    
    public void searchBySpecificStocks(int stocks, Auth dependency) {
        
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("|    No.    |    Code    |        Name        |    Stocks    |    Price    |");
        System.out.println("---------------------------------------------------------------------------");

        for (Product product : Storage.Products) {
            int productNo = Storage.Products.indexOf(product) + 1;
            if (product.getStocks() == stocks && product.productDependency.getUsername().equals(dependency.getUsername())) {
                System.out.printf(
                    "| %-8d | %-10s | %-18s | %-12d | %-10.1f |\n",
                    productNo, 
                    product.getCode(), 
                    product.getName(), 
                    product.getStocks(), 
                    product.getPrice()
                );
            }
        }
        System.out.println("---------------------------------------------------------------------------");
        
    }
    
    public void searchByStocksRange(int minimum, int maximum, Auth dependency) {
        
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("|    No.    |    Code    |        Name        |    Stocks    |    Price    |");
        System.out.println("---------------------------------------------------------------------------");

        for (Product product : Storage.Products) {
            int productNo = Storage.Products.indexOf(product) + 1;
            if (product.getStocks() >= minimum && product.getStocks() <= maximum && product.productDependency.getUsername().equals(dependency.getUsername())) {
                System.out.printf(
                    "| %-8d | %-10s | %-18s | %-12d | %-10.1f |\n",
                    productNo, 
                    product.getCode(), 
                    product.getName(), 
                    product.getStocks(), 
                    product.getPrice()
                );
            }
        }
        System.out.println("---------------------------------------------------------------------------");
        
    }
    
    public void searchBySpecificPrice(float price, Auth dependency) {
        
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("|    No.    |    Code    |        Name        |    Stocks    |    Price    |");
        System.out.println("---------------------------------------------------------------------------");

        for (Product product : Storage.Products) {
            int productNo = Storage.Products.indexOf(product) + 1;
            if (product.getPrice() == price && product.productDependency.getUsername().equals(dependency.getUsername())) {
                System.out.printf(
                    "| %-8d | %-10s | %-18s | %-12d | %-10.1f |\n",
                    productNo, 
                    product.getCode(), 
                    product.getName(), 
                    product.getStocks(), 
                    product.getPrice()
                );
            }
        }
        System.out.println("---------------------------------------------------------------------------");
        
    }
    
    public void searchByPriceRange(float minimum, float maximum, Auth dependency) {
        
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("|    No.    |    Code    |        Name        |    Stocks    |    Price    |");
        System.out.println("---------------------------------------------------------------------------");

        for (Product product : Storage.Products) {
            int productNo = Storage.Products.indexOf(product) + 1;
            if (product.getPrice() >= minimum && product.getPrice() <= maximum && product.productDependency.getUsername().equals(dependency.getUsername())) {
                System.out.printf(
                    "| %-8d | %-10s | %-18s | %-12d | %-10.1f |\n",
                    productNo, 
                    product.getCode(), 
                    product.getName(), 
                    product.getStocks(), 
                    product.getPrice()
                );
            }
        }
        System.out.println("---------------------------------------------------------------------------");
        
    }
   
    public String getCode() {
        
        return this.productCode;
    }
    
     public String getName() {
        
        return this.productName;
    }
     
     public int getStocks() {
        
        return this.productStocks;
    }
     
     public float getPrice() {
        
        return this.productPrice;
    }
     
     public boolean stacksIn(int number) {
         this.productStocks = this.productStocks + number;
         return true;
     }
     
      public boolean stacksOut(int number) {
         this.productStocks = this.productStocks - number;
         return true;
     }
      
     public boolean priceIn(float number) {
         this.productPrice = this.productPrice + number;
         return true;
     }
     
      public boolean priceOut(float number) {
         this.productPrice = this.productPrice - number;
         return true;
     }
    
}
