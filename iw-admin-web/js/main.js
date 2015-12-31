
    var menuList = [
    {id: "publishSpu",name: "发布商品"},
    {id: "manageSpu",name: "管理商品"}
    ];
    var MenuList = React.createClass({
      handleMenuClick : function(id){
        var targetMenu;
        $(menuList).each(function(index, element){
          if(element.id == id){
            targetMenu = element;
            element.actived = true;
          }else{
            element.actived = false;
          }
        })
        this.setState({data: menuList});
        PubSub.publish('menuItem', targetMenu);
      },  
      getInitialState: function() {
        return {data: menuList};
      },  
      render: function() {
        var that = this;
        var menuNodes = this.state.data.map(function(menu) {
          var activeClassName = menu.actived?"active":"";
          return (
            <li key={menu.id} className={activeClassName}><a href="#" onClick={that.handleMenuClick.bind(that, menu.id)}><i className="fa fa-link"></i> <span>{menu.name}</span></a></li>
            );
        });
        return (
          <ul className="sidebar-menu" >
            {menuNodes}
          </ul>                
          );
      }
    });

ReactDOM.render(
  <MenuList/>,
  document.getElementById('sidebar-menu')
  );

var ContentHeader = React.createClass({ 
  componentWillMount: function() {
    PubSub.subscribe('menuItem', function(topic, menuItem) {
      this.setState({ data: menuItem });
    }.bind(this));
  },
  getInitialState:function(){
    return {data:{name:"主页"}}
  } ,
  render: function() {
    var breadCrumb;
    if(this.state.data.id){
      breadCrumb = <ol className="breadcrumb"> <li><a href="#"><i className="fa fa-dashboard">主页</i> </a></li><li className="active">{this.state.data.name}</li></ol>;
    }
    return (
      <section className="content-header">
        <h1>
          {this.state.data.name}
          <small>Optional description</small>
        </h1>
        {breadCrumb}
      </section>                             
      );
  }
});

ReactDOM.render(
  <ContentHeader/>,
  document.getElementById('content-header')
  );


PubSub.subscribe('menuItem', function(topic, menuItem) {
  $("#content").load("page/"+menuItem.id+".html")
})