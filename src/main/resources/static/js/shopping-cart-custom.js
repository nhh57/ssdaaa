$(document).ready(() => {
	$('.productRemove').on('click', (element) => {
		const thisBtn = $(element.currentTarget);
		const maSp = thisBtn.data("masanpham");
		$.ajax({
			url: "/mvc/shopping-cart/deleteItem",
			method: "POST",
			// type: "application/json",
			data: {
				maSanPham: maSp
			},
			success: function(response) {
				const obj = JSON.parse(response);
				console.log(obj)
				$('#checkout_items').html(obj.soLuong);
				thisBtn.closest('.table-body-row').remove();
				if(obj.soLuong==0){
				    $('#phiShip').html('0');
				    $('#tongTien').html('0');
				    $('#phaiTra').html('0');
                }else{
                    const tongTienResult=obj.tongTien.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")+ ' đ'
                    $('#tongTien').html(tongTienResult);
                    const phaiTraResult=(obj.tongTien+15000).toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")+ ' đ'
                    $('#phaiTra').html(phaiTraResult);
                }
			}
		});
	});

	$(".inputSoLuong").change(function(element){
	 var max = parseInt($(this).attr('max'));
	 var min = parseInt($(this).attr('min'));
	 if ($(this).val() > max){
	    $(this).val(max);
	 }else if ($(this).val() < min){
	    $(this).val(min);
	 }

      const thisBtn = $(element.currentTarget);
      let soLuongSanPham=thisBtn.val();
      const maSp=thisBtn.data("masp");
      $.ajax({
        url: "/mvc/shopping-cart/changeCount",
        method: "POST",
        // type: "application/json",
        data: {
            maSanPham: maSp,
            soLuongSanPham: soLuongSanPham
        },
        success: function(response) {
            const obj = JSON.parse(response);
            console.log(obj)
            $('#checkout_items').html(obj.soLuong);
            const tongTienResult=obj.tongTien.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")+ ' đ'
            $('#tongTien').html(tongTienResult);
            const phaiTraResult=(obj.tongTien+15000).toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")+ ' đ'
            $('#phaiTra').html(phaiTraResult);
            const tongTienItem=obj.tongTienItem.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")+ ' đ'
            thisBtn.closest('.table-body-row').find('.productTotal').html(tongTienItem);
        }
    });
    });
    $(".btnAddressCustom").click(function () {
       $(".btnAddressCustom").removeClass("btnActive").addClass("btnDisable");
       	$(this).removeClass("btnDisable").addClass("btnActive");
       	const shipDetailId=$(this).data('shipid');
        const shipFullName=$(this).data('shipname');
        const shipPhone=$(this).data('shipphone');
        const shipAddress=$(this).data('shipaddress');
        $("#shipDetailId").val(shipDetailId);
        $("#fullName").val(shipFullName);
        $("#phone").val(shipPhone);
        $("#address").val(shipAddress);
       	console.log("shipId:",shipDetailId);
    });
    $("#modalNotify").modal('show');
});