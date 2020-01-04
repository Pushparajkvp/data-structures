package dev.pushparaj;

public class UnionFind {

    private int componentsCount = 0;

    private int[] size = null;

    private int[] parent = null;

    private int numNodes = 0;

    public UnionFind(int size) {
        if(size <= 0)
            throw new IllegalArgumentException("Size cannot be negative");

        this.size = new int[size];
        this.parent = new int[size];
        this.componentsCount = size;
        this.numNodes = size;

        for(int i = 0; i < size; i++) {
            this.parent[i] = -1;
            this.size[i] = 1;
        }
    }

    public int find(int element) {

        if(element < 0 || element >= numNodes)
            throw new IllegalArgumentException("Find element not present during construction of union find");

        int root = element;
        while (parent[root] != -1) root = parent[root];

        int it = element;
        while (it != root) {
            int nextElement = parent[element];
            parent[element] = root;
            it = nextElement;
        }

        return root;
    }

    public void unify(int element1, int element2) {

        if(element1 < 0 || element1 >= numNodes || element2 < 0 || element2 >= numNodes)
            throw new IllegalArgumentException("elements not present during construction of union find");

        int compRoot1 = find(element1);
        int compRoot2 = find(element2);

        if(compRoot1 != compRoot2) {
            if(size[compRoot1] >= size[compRoot2]) {
                size[compRoot1] += size[compRoot2];
                parent[compRoot2] = compRoot1;
            } else {
                size[compRoot2] += size[compRoot1];
                parent[compRoot1] = compRoot2;
            }
            componentsCount--;
        }

    }

    public boolean connected(int comp1, int comp2) {
        return find(comp1) == find(comp2);
    }

    public int componentSize(int comp) {
        return size[find(comp)];
    }

    public int size() {
        return numNodes;
    }

    public int components() {
        return componentsCount;
    }
}
