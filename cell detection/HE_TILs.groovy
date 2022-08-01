//Script to quantify TILs in H&E stained-images of advanced and metastatic non-small cell lung cancer
//Image format: Aperio SVS 8-bit
//Pixel size: 0.49μm
//Created using QuPath v0.3.2
//NB: These scripts are not ready to use, they must be adjusted for local variations in image format and etc. Thus, it could be used as starting point and base for local adapatation. 


//Set stain vectors and estimate background values
setImageType('BRIGHTFIELD_H_E');
setColorDeconvolutionStains('{"Name" : "H&E default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "Eosin", "Values 2" : "0.2159 0.8012 0.5581", "Background" : " 255 255 255"}');

//ROI selection is required in case using vector auto-estimation for stain normalization
setImageType('BRIGHTFIELD_H_E');
setColorDeconvolutionStains('{"Name" : "H&E estimated", "Stain 1" : "Hematoxylin", "Values 1" : "0.83459 0.47582 0.27759", "Stain 2" : "Eosin", "Values 2" : "0.24581 0.68069 0.6901", "Background" : " 188 122 163"}');

//Use manual annotation tools of QuPath to select ROIs for all project images before the analysis. The other options could be using either simple tissue detection or pixel classifier to create the annotations of the ROIs.
//Cell detection
selectAnnotations();
runPlugin('qupath.imagej.detect.nuclei.WatershedCellDetection', '{"detectionImageBrightfield": "Hematoxylin OD",  "requestedPixelSizeMicrons": 0.25,  "backgroundRadiusMicrons": 10.0,  "medianRadiusMicrons": 1,  "sigmaMicrons": 1.5,  "minAreaMicrons": 7.0,  "maxAreaMicrons": 500.0,  "threshold": 0.11,  "maxBackground": 2.0,  "watershedPostProcess": true,  "cellExpansionMicrons": 2.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

//Adding intensity features
selectDetections();
runPlugin('qupath.lib.algorithms.IntensityFeaturesPlugin', '{"pixelSizeMicrons": 0.25,  "region": "ROI",  "tileSizeMicrons": 25.0,  "colorOD": true,  "colorStain1": true,  "colorStain2": true,  "colorStain3": true,  "colorRed": true,  "colorGreen": true,  "colorBlue": true,  "colorHue": true,  "colorSaturation": true,  "colorBrightness": true,  "doMean": true,  "doStdDev": true,  "doMinMax": true,  "doMedian": true,  "doHaralick": true,  "haralickDistance": 1,  "haralickBins": 32}');

//Adding smoothed features
selectAnnotations();
runPlugin('qupath.lib.plugins.objects.SmoothFeaturesPlugin', '{"fwhmMicrons": 25.0,  "smoothWithinClasses": false,  "useLegacyNames": false}');
selectAnnotations();
runPlugin('qupath.lib.plugins.objects.SmoothFeaturesPlugin', '{"fwhmMicrons": 50.0,  "smoothWithinClasses": false,  "useLegacyNames": false}');
selectAnnotations();
runPlugin('qupath.lib.plugins.objects.SmoothFeaturesPlugin', '{"fwhmMicrons": 100.0,  "smoothWithinClasses": false,  "useLegacyNames": false}');

//Run object classifier based on tissue type
//It is highly recommended to train your own cell classifier.
//AS an example cell classifier for metastatic liver tissues is uploaded.
selectAnnotations();
runObjectClassifier("/...path.../lung.json")

selectAnnotations();
runObjectClassifier("/...path.../LN.json")

selectAnnotations();
runObjectClassifier("/...path.../pleura.json")

selectAnnotations();
runObjectClassifier("/...path.../brain.json")

selectAnnotations();
runObjectClassifier("/...path.../liver.json")

selectAnnotations();
runObjectClassifier("/...path.../soft_tissue.json")

selectAnnotations();
runObjectClassifier("/...path.../other.json")