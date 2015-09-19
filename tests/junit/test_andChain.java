class Main { 
    public static void main(String[] args) { 
        TestClass c; 
         
        c = new TestClass(); 
        if(c.calculate()) { 
            System.out.println(13); 
        } else { 
            System.out.println(4); 
        } 
         
        if(c.isCtriggered()) { 
            System.out.println(2); 
        } else { 
            System.out.println(37); 
        } 
    } 
} 
 
class TestClass { 
    boolean isCtriggered; 
 
    public boolean triggerA() { 
        return true; 
    }     
    public boolean triggerB() { 
        return false; 
    }     
    public boolean triggerC() { 
        isCtriggered = true; 
        return true; 
    }     
    public boolean calculate() { 
        isCtriggered = false; 
        return this.triggerA() && this.triggerB() && this.triggerC(); 
    } 
     
    public boolean isCtriggered() { 
        return isCtriggered; 
    } 
}