import Foundation
import ComposeApp

@MainActor
final class HomeViewModel: ObservableObject {

    @Published var posts: [Post] = []
    @Published var isLoading: Bool = false
    @Published var error: String? = nil

    private let apiService: ApiService


    init() {
        let client = NetworkClientKt.createHttpClient()
        self.apiService = ApiService(client: client)
    }

    func loadPosts() {
        isLoading = true
        error = nil

        apiService.getPosts { [weak self] result, error in
            guard let self else { return }

            DispatchQueue.main.async {
                self.isLoading = false

                if let error = error {
                    self.error = error.localizedDescription
                    return
                }

                if let result = result {
                    self.posts = result
                } else {
                    self.error = "Invalid data"
                }
            }
        }
    }
}
