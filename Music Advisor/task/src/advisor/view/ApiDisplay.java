package advisor.view;

import java.util.List;

public class ApiDisplay {
    private int currentPage;
    private int totalPages;
    private List<String> retrievedContents;
    private int maxPerPage;

    public ApiDisplay(int maxPerPage) {
        this.maxPerPage = maxPerPage;
    }

    public void setRetrievedContents(List<String> retrievedContents) {
        nullifyState();
        this.retrievedContents = retrievedContents;
        this.totalPages = (int) Math.ceil((double) this.retrievedContents.size() / this.maxPerPage);
    }

    public void display() {
        this.retrievedContents.stream()
                .skip(this.maxPerPage * this.currentPage)
                .limit(this.maxPerPage)
                .forEach((item) -> System.out.println(item + System.lineSeparator()));

        System.out.println("---PAGE " + (this.currentPage + 1) +
                " OF " + this.totalPages + "---");
    }

    public void next() {
        if (this.retrievedContents == null) {
            throw new IllegalStateException("Retrieve contents first");
        } else if (this.currentPage + 1 == this.totalPages) {
            throw new IllegalStateException("No more pages.");
        }

        this.currentPage++;
    }

    public void prev() {
        if (this.retrievedContents == null) {
            throw new IllegalStateException("Retrieve contents first");
        } if (this.currentPage == 0) {
            throw new IllegalStateException("No more pages.");
        }

        this.currentPage--;
    }

    private void nullifyState() {
        this.currentPage = 0;
        this.retrievedContents = null;
    }
}
