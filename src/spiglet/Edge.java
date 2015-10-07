package spiglet;

public class Edge<L, R> extends ASTNode<ASTNode>{
    public L left;
    public R right;
    
    public Edge(L left, R right) {
        this.left = left;
        this.right = right;
    }

}
