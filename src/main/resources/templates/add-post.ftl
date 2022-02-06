<#import "parts/layout.ftl" as layout>
<@layout.page>
        <div class="page-body">
          <div class="container-fluid">
            <div class="page-header">
              <div class="row">
                <div class="col-lg-6 main-header">
                  <h2>Add<span>Post</span></h2>
                  <h6 class="mb-0">admin panel</h6>
                </div>
                <div class="col-lg-6 breadcrumb-right">
                  <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/"><i class="pe-7s-home"></i></a></li>
                    <li class="breadcrumb-item">Apps    </li>
                    <li class="breadcrumb-item">Blog</li>
                    <li class="breadcrumb-item active">Add Post</li>
                  </ol>
                </div>
              </div>
            </div>
          </div>
          <!-- Container-fluid starts-->
          <div class="container-fluid">
            <div class="row">
              <div class="col-sm-12">
                <div class="card">
                  <div class="card-header">
                    <h5>Post Edit</h5>
                  </div>
                  <div class="card-body add-post">
                    <form class="row needs-validation themeform" novalidate="">
                      <div class="col-sm-12">
                        <div class="form-group">
                          <label for="validationCustom01">Title:</label>
                          <input class="form-control" id="validationCustom01" type="text" placeholder="Post Title" required="">
                          <div class="valid-feedback">Looks good!</div>
                        </div>
                        <div class="form-group">
                          <label>Type:</label>
                          <div class="m-checkbox-inline">
                            <label for="edo-ani">
                              <input class="radio_animated" id="edo-ani" type="radio" name="rdo-ani" checked="">Text
                            </label>
                            <label for="edo-ani1">
                              <input class="radio_animated" id="edo-ani1" type="radio" name="rdo-ani">Image
                            </label>
                            <label for="edo-ani2">
                              <input class="radio_animated" id="edo-ani2" type="radio" name="rdo-ani" checked="">Audio
                            </label>
                            <label for="edo-ani3">
                              <input class="radio_animated" id="edo-ani3" type="radio" name="rdo-ani">Video
                            </label>
                          </div>
                        </div>
                        <div class="form-group">
                          <div class="col-form-label">Category:
                            <select class="js-example-placeholder-multiple col-sm-12" multiple="multiple">
                              <option value="AL">Lifestyle</option>
                              <option value="WY">Travel</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group">
                          <label>Content:</label>
                          <textarea id="text-box" name="text-box" rows="4"></textarea>
                        </div>
                      </div>
                    </form>
                    <form class="dropzone digits" id="singleFileUpload" action="/upload.php">
                      <div class="m-0 dz-message needsclick"><i class="icon-cloud-up"></i>
                        <h6 class="mb-0">Drop files here or click to upload.</h6>
                      </div>
                    </form>
                    <div class="btn-showcase">
                      <button class="btn btn-primary btn-pill" type="submit">Post</button>
                      <input class="btn btn-light btn-pill" type="reset" value="Discard">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- Container-fluid Ends-->
        </div>
</@layout.page>