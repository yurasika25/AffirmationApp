import SwiftUI
import ComposeApp

struct HomeView: View {

    @StateObject private var vm = HomeViewModel()

    var body: some View {
        NavigationStack {
            Group {
                if vm.isLoading {
                    ProgressView("Loading...")
                } else if let error = vm.error {
                    VStack(spacing: 12) {
                        Text("Error")
                            .font(.headline)
                        Text(error)
                            .multilineTextAlignment(.center)
                        Button("Retry") {
                            vm.loadPosts()
                        }
                    }
                    .padding()
                } else {
                    List(vm.posts, id: \.id) { post in
                        NavigationLink {
                            DetailsView(post: post)
                        } label: {
                            VStack(alignment: .leading, spacing: 6) {
                                Text(post.title)
                                    .font(.headline)
                                    .lineLimit(2)

                                Text(post.body)
                                    .font(.body)
                                    .foregroundStyle(.secondary)
                                    .lineLimit(2)
                            }
                            .padding(.vertical, 6)
                        }
                    }
                    .listStyle(.plain)
                }
            }
            .navigationTitle("Home")
            .navigationBarTitleDisplayMode(.large)
            .onAppear {
                if vm.posts.isEmpty {
                    vm.loadPosts()
                }
            }
        }
    }
}
