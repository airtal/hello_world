class ConsistentHashing {

private HashFunction hashFunction;
private int numberOfReplicas;
private TreeMap<Integer, T> circle;

public ConsistentHashing(Collection<T> nodes, HashFunction hashFunction, int numberOfReplicas) {
  this.hashFunction = hashFunction;
  this.numberOfReplicas = numberOfReplicas;
  
  for (T node : nodes) {
    addNode(node);
  }
}

private void addNode(T node) {
  for (int i = 0; i < numberOfReplicas; ++i) {
    circle.put(hashFunction.hash(node.toString() + i), node);
  }
}

private void deleteNode(T node) {
  for (int i = 0; i < numberOfReplicas; ++i) {
    circle.remove(hashFunction.hash(node.toString() + i));
  }
}

private T getNode(String key) {
  if (circle.isEmpty()) {
    return null;
  }
  int hash = hashFunction.hash(key);
  if (!circle.containsKey(hash)) {
    SortedMap<Integer, T> tailMap = circle.tailMap(hash);
    hashKey = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
  }
  return circle.get(hash);
}

}
