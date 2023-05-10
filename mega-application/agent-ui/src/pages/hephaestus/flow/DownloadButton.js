import React from 'react';
import { toPng,toCanvas} from 'html-to-image';

function downloadImage(dataUrl) {
  const a = document.createElement('a');

  a.setAttribute('download', '流程.png');
  a.setAttribute('href', dataUrl);
  a.click();
}

function DownloadButton() {
  const onClick = () => {
    toCanvas(document.querySelector('.react-flow'))
      .then(function (canvas) {
        console.log(canvas)
        console.log(canvas.toDataURL('image/png',1.0))
       let dataUrl = canvas.toDataURL()
        downloadImage(dataUrl);
       });
    // toPng(document.querySelector('.react-flow'), {
    //   filter: (node) => {
    //     // we don't want to add the minimap and the controls to the image
    //     if (
    //       node?.classList?.contains('react-flow__minimap') ||
    //       node?.classList?.contains('react-flow__controls')
    //     ) {
    //       return false;
    //     }
    //
    //     return true;
    //   },
    // }).then(downloadImage);
  };

  return (
    <button className="download-btn" onClick={onClick}>
      Download Image
    </button>
  );
}

export default DownloadButton;
