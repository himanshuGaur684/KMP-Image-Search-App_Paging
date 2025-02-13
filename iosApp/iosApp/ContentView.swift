import SwiftUI
import shared
import KMPObservableViewModelSwiftUI

struct ContentView: View {
    let greet = Greeting().greet()
    
    @StateViewModel var viewModel = ImageViewModel()
    
    @State private var searchText = ""
    
    var body: some View {
        NavigationStack{
            List{
                Section{
                    if viewModel.iosUiState.isPrepend {
                         VStack {
                            ProgressView("Loading...") // ✅ Shows loader properly
                                .progressViewStyle(CircularProgressViewStyle())
                                .padding()
                        }
                        .frame(maxWidth: .infinity)
                        .frame(height: 155)
                        .background(Color.gray.opacity(0.1))
                    }
                }
               
                
                if viewModel.iosUiState.isRefresh {
                    VStack {
                        ProgressView("Refreshing...")
                            .progressViewStyle(CircularProgressViewStyle())
                            .padding()
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .background(Color.gray.opacity(0.1))
                } else{
                    ForEach(viewModel.hitsSnapshotList.indices, id: \.self) { index in
                            if let pixabayImage = viewModel.getElement(index: Int32(index)){
                                AsyncImage(url: URL(string: pixabayImage.largeImageURL)) { image in
                                    image.resizable()
                                        .aspectRatio(contentMode: .fit)
                                        .frame(height: 300)
                                        .frame(maxWidth: .infinity)
                                        .clipped()
                                } placeholder: {
                                    ZStack{
                                        ProgressView()
                                        
                                    }
                                    .frame(maxWidth: .infinity)
                                    .frame(height: 300)
                                    
                                }
                            }
                        }
                    
                    
                }
                Section{
                    if viewModel.iosUiState.isAppend {
                        VStack {
                                    ProgressView("Appending...")
                                        .progressViewStyle(CircularProgressViewStyle())
                                        .padding()
                                }
                                .frame(maxWidth: .infinity)
                                .frame(height: 155)
                                .background(Color.gray.opacity(0.1))
                    }
                }
                
              
            }
            .navigationTitle("Images")
            .searchable(text: $searchText)
            .onChange(of: searchText) { newValue in
                viewModel.updateQuery(q: newValue)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
