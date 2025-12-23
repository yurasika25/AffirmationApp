import SwiftUI
import ComposeApp

struct DetailsView: View {
    let post: Post

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 12) {
                Text("Post #\(post.id)")
                    .font(.subheadline)
                    .foregroundStyle(.secondary)

                Text(post.title)
                    .font(.title2)
                    .fontWeight(.semibold)

                Text(post.body)
                    .font(.body)
                    .foregroundStyle(.secondary)
            }
            .padding()
        }
        .navigationTitle("Details")
        .navigationBarTitleDisplayMode(.inline)
    }
}
